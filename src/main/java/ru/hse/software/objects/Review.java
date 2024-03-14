package ru.hse.software.objects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class Review implements Serializable {
    @Builder.Default
    public int mark = 0;
    @Builder.Default
    public String review = null;
    public Review(int mark,String review){
        this.mark = mark;
        this.review = review;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Отзыв {");
        sb.append("Оценка: ").append(mark);
        sb.append(", Текстовый комментарий: ").append(review);
        sb.append("}");
        return sb.toString();
    }
}
