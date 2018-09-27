package com.ahav.email.mapper;

        import org.apache.ibatis.annotations.Mapper;

        import com.ahav.email.pojo.Email;

        import java.util.List;

@Mapper
public interface EmailMapper {
    boolean insert(Email record);
    Email selectEmail();
    boolean updateEmail(Email record);
}
