package com.example.potato.sic9.dto.article;

import com.example.potato.sic9.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Builder
@ToString
public class ArticleRequestDto {
    private String place;
    private String comment;

    private String meetAt;
    private User user;

    public ArticleRequestDto of(User user) {
        return new ArticleRequestDto(this.place, this.comment, this.meetAt, user);
    }

    @Builder
    public ArticleRequestDto(String place, String comment, String meetAt, User user) {
        this.place = place;
        this.comment = comment;
        this.meetAt = meetAt;
        this.user = user;
    }
}
