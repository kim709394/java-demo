package com.kim.common;

import com.kim.common.PropertiesCopyTest.MapStruct.Car;
import com.kim.common.PropertiesCopyTest.MapStruct.CarMapper;
import com.kim.common.PropertiesCopyTest.MapStruct.Engine;
import com.kim.common.PropertiesCopyTest.MapStruct.Wheel;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-13T17:25:21+0800",
    comments = "version: 1.5.0.Beta1, compiler: javac, environment: Java 1.8.0_221 (Oracle Corporation)"
)
public class PropertiesCopyTest$MapStruct$CarMapperImpl implements CarMapper {

    @Override
    public Car engineFuseWheel2Car(Engine engine, Wheel wheel) {
        if ( engine == null && wheel == null ) {
            return null;
        }

        Car car = new Car();

        if ( engine != null ) {
            car.setEnginePower( engine.getPower() );
        }
        if ( wheel != null ) {
            car.setWheelSize( wheel.getSize() );
        }

        return car;
    }
}
