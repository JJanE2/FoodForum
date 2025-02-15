package ourfood.example.foodforum.paging;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PaginationUtil {
    public class PageDTO {
        private int startPage;
        private int endPage;

        public PageDTO(int startPage, int endPage) {
            this.startPage = startPage;
            this.endPage = endPage;
        }

        public int getStartPage() {
            return startPage;
        }
        public int getEndPage() {
            return endPage;
        }
    }

    public PageDTO calculatePageDTO(Page<?> entityPage) {
        int currentPage = entityPage.getNumber();
        int totalPages = entityPage.getTotalPages();

        // endPage 계산
        int endPage = Math.min((currentPage / 10 + 1) * 10 - 1, totalPages - 1);
        // startPage 계산
        int startPage = Math.max(endPage - 9, 0);

        return new PageDTO(startPage, endPage);
    }
}
