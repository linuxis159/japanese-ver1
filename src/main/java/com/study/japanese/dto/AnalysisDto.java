package com.study.japanese.dto;

import com.study.japanese.function.DateSetting;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

public class AnalysisDto {

    @ToString
    @Getter
    @Setter
    public static class ViewCountByDate implements Comparable<AnalysisDto.ViewCountByDate>{
        private String view_;
        private Date date_;

        public String getDate_() {
            return DateSetting.setDateFormat(this.date_);
        }
        @Override
        public int compareTo(AnalysisDto.ViewCountByDate o) {
            return this.date_.compareTo(o.date_);
        }

    }

    @ToString
    @Getter
    @Setter
    public static class PostCountByDate implements Comparable<AnalysisDto.PostCountByDate>{
        private String postCount_;
        private Date date_;

        public String getDate_() {
            return DateSetting.setDateFormat(this.date_);
        }
        @Override
        public int compareTo(PostCountByDate o) {
            return this.date_.compareTo(o.date_);
        }

    }

    @ToString
    @Getter
    @Setter
    public static class CommentCountByDate implements Comparable<CommentCountByDate>{
        private String commentCount_;
        private Date date_;

        public String getDate_() {
            return DateSetting.setDateFormat(this.date_);
        }
        @Override
        public int compareTo(CommentCountByDate o) {
            return this.date_.compareTo(o.date_);
        }

    }
}
