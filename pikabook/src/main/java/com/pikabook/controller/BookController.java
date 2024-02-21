package com.pikabook.controller;

import com.pikabook.entity.Book;
import com.pikabook.entity.BookDto;
import com.pikabook.service.BookService;
import com.pikabook.enumClass.SearchType;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
@ToString
@RestController
public class BookController {
    private final BookService bookService;


    @PostMapping("/")
    public Book addBooK(@RequestBody Book book) {
        bookService.save(book);
        return book;
    }


    @GetMapping("/{id}")
    public Book getBook(@PathVariable("id") Long id) {
        Book book = bookService.findById(id);
        return book;
    }


    /**
     * 장르로 키워드 뽑기(8개)
     * 판타지
     * 영화드라마원작
     * 인간드라마
     * 실존주의
     * 사회비판
     * 로맨스
     * 노벨상수상자
     * 한국문학
     * <p>
     * 1. 이 중에서 장르(tag) 하나 고름
     * 2. 장르에 해당하는 책들 목록 select
     * 3. 그 책 중에서 키워드 추출
     * 4. 중복 제거, 배열 변환, 반환
     *
     * @param tag
     * @return
     */
    // 장르(8개)로 키워드 뽑기

    @GetMapping("/keyword")
    public String[] getKeywordByGenre(@RequestParam("tag") String tag) {
        List<Book> books = bookService.findByGenre(tag);
        log.info("갯수 : " + books.size());

        List<String> keywordList = new ArrayList<>();
        for (Book book : books) {
            String keyword = book.getKeywords();

            keyword = keyword.replaceAll("[\\[\\]']", "");
            String[] split = keyword.split(", ");

            for (String s : split) {
//                if (!keywords.contains(s)) {
//                    keywords.add(s);
//                }
                keywordList.add(s);
            }
        }
        System.out.println("keywords = " + keywordList);
        String[] keywords = keywordList.toArray(String[]::new);

        return keywords;
    }


    @GetMapping("/books")
    public Object getBookByIsbnOrGenre(@RequestParam(value = "searchType", required = true) String searchType,
                                       @RequestParam(value = "isbns", required = false) List<String> isbns,
                                       @RequestParam(value = "keywords", required = false) List<String> keywords) {

        // 파라미터 개수 5개를 초과하는지 확인


        List<BookDto> bookDtos = new ArrayList<>();

        if (searchType.equals(SearchType.isbn.toString())) {
            if (isbns == null || isbns.isEmpty()) {
                return "ISBN 파라미터 없음";
            }
            if (isbns.size() > 5) {
                return "Error: Too many ISBN parameters. Maximum 5 parameters are allowed.";
            }

            for (String isbn : isbns) {
                Book book = bookService.findByIsbn(isbn);
                bookDtos.add(book.toDto());
            }

            return bookDtos;
        } else if (searchType.equals(SearchType.genre_and_keyword.toString())) {
            if (keywords == null || keywords.isEmpty()) {
                return "keyword 파라미터 없음";
            }
            if (keywords.size() > 5) {
                return "Error: Too many keywords parameters. Maximum 5 parameters are allowed.";
            }

            /**
             *1. 키워드를 하나라도 가진 책으로 filtering
             * 2 sorting
             * 선택한 키워드를 많이 가진 책부터
             *
             *
             * 선택한 키워드가 키워드 리스트의 앞쪽에 있는 책부터  ->
             *
             */

            // 키워드 개수 map
            Map<Book, Integer> rankMap = new HashMap<>();


            for (String keyword : keywords) {
                // 엑셀에서 따옴표를 기준으로 키워드가 split 되므로 작은 따옴표 앞뒤로 추가
                keyword = "\'" + keyword + "\'";
                List<Book> books = bookService.findByKeyword(keyword);

                System.out.println("books.size() 1 = " + books.size());

                for (Book book : books) {
                    rankMap.put(book, rankMap.getOrDefault(book, 0) + 1);
                }
            }
            //정렬
            List<Book> keySet = new ArrayList<>(rankMap.keySet());

            keySet.sort(new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    return rankMap.get(o2).compareTo(rankMap.get(o1));
                }
            });

            int i = 0;
            for (Book book : keySet) {
                BookDto dto = book.toDto();
                bookDtos.add(dto);
                i++;
                if (i > 5) {
                    break;
                }
                System.out.print("Key : " + book.getTitle());
                System.out.println(", Val : " + rankMap.get(book));
            }

//            System.out.println("rankMap.size() = " + rankMap.size());
            return bookDtos;
        }


        return null;
    }

}

