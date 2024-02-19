package com.pikabook;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
@ToString
public class BookController {
    private final BookService bookService;


    @ResponseBody
    @PostMapping("/")
    public Book addBooK(@RequestBody Book book) {
        bookService.save(book);
        return book;
    }

    @ResponseBody
    @GetMapping("/{id}")
    public Book getBook(@PathVariable("id") Long id) {
        Book book = bookService.findById(id);
        return book;
    }


    /**
     * 장르로 키워드 뽑기
     * 판타지
     * 영화드라마원작
     * 인간드라마
     * 실존주의
     * 사회비판
     * 로맨스
     * 노벨상수상자
     * <p>
     * 1. 이 중에서 장르(tag) 하나 고름
     * 2. 장르에 해당하는 책들 목록 select
     * 3. 그 책 중에서 키워드 추출 - json형태
     * 4. 중복 제거, 반환
     *
     * @param tag
     * @return
     */
    @ResponseBody
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

    @ResponseBody
    @GetMapping("/books")
    public Book getBookByIsbn(@RequestParam(value = "searchType", required = true) String searchType,
                              @RequestParam("isbn") String isbn) {

        if (searchType.equals(SearchType.isbn.toString())) {
            Book book = bookService.findByIsbn(isbn);
            return book;
        } else if (searchType.equals(SearchType.genre_and_keywords)) {
            /**
             *
             * 코드 짜야함
             */return null;
        }
        log.info("3");
        return null;
    }
}
