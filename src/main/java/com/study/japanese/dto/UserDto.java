package com.study.japanese.dto;

import com.study.japanese.function.DateSetting;
import com.study.japanese.role.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.ObjectError;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.List;

import static com.study.japanese.constraint.Constants.User.*;

@Getter
@Setter
@ToString
public class UserDto {
   private String id;

   private String password;

   private String name;

   private String email;

   private Timestamp createdDate;

   private UserRole role = UserRole.USER;

   private int banCheck;


   public String getCreatedDate(){
      return DateSetting.setDateFormat(this.createdDate);
   }

   @Getter
   @Setter
   @ToString
   public static class JoinRequest{
      @NotBlank(message = EMPTY_USERID)
      @Size(max = USERID_MAX)
      private String id;

      @NotBlank(message = EMPTY_PASS)
      @Size(max = PASS_MAX)
      private String password;

      @NotBlank(message = EMPTY_PASS)
      @Size(max = PASS_MAX)
      private String passwordConfirm;

      @NotBlank(message = EMPTY_NAME)
      @Size(max = NAME_MAX)
      private String name;

      @NotBlank(message = EMPTY_EMAIL)
      @Size(max = MEAIL_MAX)
      @Email
      private String email;

      @NotNull(message = "이메일인증이 제대로 되지 않았습니다")
      private String code;
   }

   @Getter
   @Setter
   public static class JoinResponse{
      private List<ObjectError> errors;
   }

   @Getter
   @Setter
   public static class InfoResponse{
      private String name;
      private String email;
   }

   public static class updateNameRequest{
      private String username;
   }
   @Getter
   @Setter
   public static class UserPassFindRequest{
      private String userId;
   }

   @Getter
   @Setter
   public static class UserDeleteRequest{
      @NotBlank(message = EMPTY_PASS)
      @Size(max = PASS_MAX)
      private String userPass;
   }




}
