package com.icebear.news.model;

import java.util.List;
import java.util.Objects;

public class NewsResponse {
    public Integer totalResult;
    public List<Article> articles;
    public String status;

    public String message;
    public String code;



    @Override
    public String toString() {
        return "NewsResponse{" +
                "totalResult=" + totalResult +
                ", articles=" + articles +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsResponse that = (NewsResponse) o;
        return Objects.equals(totalResult, that.totalResult) && Objects.equals(articles, that.articles) && Objects.equals(status, that.status) && Objects.equals(message, that.message) && Objects.equals(code, that.code);
    }



    @Override
    public int hashCode() {
        return Objects.hash(totalResult, articles, status, message, code);
    }
}
