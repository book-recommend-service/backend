package com.pikabook.controller;

import com.pikabook.entity.Book;
import com.pikabook.entity.BookDto;
import com.pikabook.entity.SearchType;
import com.pikabook.service.BookService;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RequestMapping("/api")
@ToString
@RestController
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


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

        HashSet<String> keywordSet = new HashSet<>();
        for (Book book : books) {
            String keyword = book.getKeywords();

            keyword = keyword.replaceAll("[\\[\\]']", "");
            String[] split = keyword.split(", ");

            keywordSet.addAll(Arrays.asList(split));
        }

        String[] keywords = keywordSet.toArray(String[]::new);

        return keywords;
    }


    @GetMapping("/books")
    public Object getBookByIsbnOrGenre(@RequestParam(value = "searchType", required = true) String searchType,
                                       @RequestParam(value = "isbns", required = false) List<String> isbns,
                                       @RequestParam(value = "keywords", required = false) List<String> keywords,
                                       @RequestParam(value = "genre", required = true)String genre) {

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
             * 선택한 키워드가 키워드 리스트의 앞쪽에 있는 책부터  ->
             *
             */

            // 키워드 순위 map
            Map<Book, List<Integer>> rankMap = new HashMap<>();
            // list의 첫번째 원소- 선택한 키워드가 키워드 리스트의 앞쪽에 있는 책부터(키워드 순서)
            // list의 두번쨰 원소 - 두번째 선택한 키워드를 많이 가진 책부터
//            정렬 - 두번째 -> 첫번쨰

            for (String keyword : keywords) {

                //비교 위한 오리지널 keyword
                String oriKeyword = keyword;

                keyword = "\'" + keyword + "\'";
                List<Book> books = bookService.findByKeywordAndGenre(keyword,genre);

                //첫번째 원소 선택한 키워드가 키워드 리스트의 앞쪽에 있는 책부터
                for (Book book : books) {
                    String allKeywords = book.getKeywords();

                    //data상 불필요한 괄호,따옴표 제거
                    allKeywords = allKeywords.replaceAll("[\\[\\]']", "");
                    String[] keywordSplit = allKeywords.split(",");

                    // 키워드 순서 index
                    int keywordOrder = 0;
                    for (int i = 0; i < keywordSplit.length; i++) {
                        if (oriKeyword.equals(keywordSplit[i].trim())) {
                            keywordOrder = i;
                            break;
                        }
                    }

                    List<Integer> list = rankMap.get(book);
                    int second;

                    if (list == null) {
                        second = 0;
                    } else {
                        second = list.get(1);
                    }
                    List<Integer> tempList = new ArrayList<>();
                    tempList.add(keywordOrder); // keyword 순서
                    tempList.add(second);
                    rankMap.put(book, tempList);
                }


//                두번쨰 원소 - 두번째 선택한 키워드를 많이 가진 책부터
                for (Book book : books) {
                    List<Integer> list = rankMap.get(book);
                    int first = list.get(0);
                    int second = list.get(1);

                    List<Integer> tempList = new ArrayList<>();
                    tempList.add(first);
                    if (second == 0) {
                        tempList.add(1);
                    } else { // 두번째 원소가 0이 아니라면
                        tempList.add(++second);
                    }
                    rankMap.put(book, tempList);
                }

            }
            List<Book> keySet = new ArrayList<>(rankMap.keySet());

            sort(keySet, rankMap);


            log.info("총 책 권수 : "+ keySet.size());
            int responseCount = 5;
            for (Book book : keySet) {
                BookDto dto = book.toDto();
                bookDtos.add(dto);
                responseCount--;
                if (responseCount == 0) {
                    break;
                }
            }

            return bookDtos;
        }
        return "Error";
    }

    private void sort(List<Book> keySet, Map<Book, List<Integer>> rankMap) {
        keySet.sort(new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                // 키워드 갯수로 정렬
                int rankComparison = rankMap.get(o2).get(1).compareTo(rankMap.get(o1).get(1));

                //키워드 순서로 정렬
                if (rankComparison == 0) {
                    int rankComparison2 = rankMap.get(o1).get(0).compareTo(rankMap.get(o2).get(0));
                    return rankComparison2;
                } else {
                    return rankComparison;
                }
            }
        });
    }

}

