package com.kim.common;

import com.kim.common.PropertiesCopyTest.MapStruct.UserDO;
import com.kim.common.PropertiesCopyTest.MapStruct.UserDO.Gender;
import com.kim.common.PropertiesCopyTest.MapStruct.UserMapper;
import com.kim.common.PropertiesCopyTest.MapStruct.UserVO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-31T21:39:43+0800",
    comments = "version: 1.5.0.Beta1, compiler: javac, environment: Java 1.8.0_221 (Oracle Corporation)"
)
public class PropertiesCopyTest$MapStruct$UserMapperImpl implements UserMapper {

    @Override
    public UserVO userDO2UserVO(UserDO userDO) {
        if ( userDO == null ) {
            return null;
        }

        UserVO userVO = new UserVO();

        userVO.setGender( userDOGenderName( userDO ) );
        if ( userDO.getCreateTime() != null ) {
            userVO.setCreateTime( new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" ).format( userDO.getCreateTime() ) );
        }
        userVO.setId( userDO.getId() );
        userVO.setName( userDO.getName() );

        return userVO;
    }

    @Override
    public List<UserVO> userDOs2UserVOs(List<UserDO> userDOs) {
        if ( userDOs == null ) {
            return null;
        }

        List<UserVO> list = new ArrayList<UserVO>( userDOs.size() );
        for ( UserDO userDO : userDOs ) {
            list.add( userDO2UserVO( userDO ) );
        }

        return list;
    }

    private String userDOGenderName(UserDO userDO) {
        if ( userDO == null ) {
            return null;
        }
        Gender gender = userDO.getGender();
        if ( gender == null ) {
            return null;
        }
        String name = gender.name;
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
