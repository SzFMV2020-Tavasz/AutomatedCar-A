package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DependsOn {

    Class<? extends SystemComponent>[] components();

}
