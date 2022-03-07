package com.gt.scr.movie.system;

import com.gt.scr.movie.audit.MovieCreateEvent;
import com.gt.scr.movie.audit.MovieUpdateEvent;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AnnotationsTest {

    private static final String[] packages = {
            "com/gt/scr/movie/service",
            "com/gt/scr/movie/audit",
            "com/gt/scr/movie/dao",
            "com/gt/scr/movie/resource",
            "com/gt/scr/movie/filter"
    };

    private static final Set<Class> exclusions = Set.of(
            MovieCreateEvent.class,
            MovieUpdateEvent.class
    );

    @Test
    void checkAllImplementationsAndResourcesHaveAppropriateAnnotation() {
        Set<Class> interfaces = new HashSet<>();
        Set<Class> allClasses = new HashSet<>();

        Arrays.stream(packages).forEach(packageName -> {
            String dottedPackageName = packageName.replace("/", ".");
            Enumeration<URL> resources = null;
            try {
                resources = AnnotationsTest.class.getClassLoader().getResources(packageName);


                assert resources != null;
                while (resources.hasMoreElements()) {
                    try (BufferedReader dis = new BufferedReader(new InputStreamReader(resources.nextElement().openStream()))) {
                        String line = null;
                        while ((line = dis.readLine()) != null) {
                            if (line.endsWith(".class") && !line.contains("Test")) {
                                Class<?> classFound = Class.forName(dottedPackageName + "." + line.substring(0, line.lastIndexOf('.')));
                                if (classFound.isInterface()) {
                                    interfaces.add(classFound);
                                } else {
                                    allClasses.add(classFound);
                                }
                            }
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        allClasses.forEach(clazz -> {
            Class[] interfaces1 = clazz.getInterfaces();

            if (!exclusions.contains(clazz)) {
                Arrays.stream(interfaces1)
                        .forEach(interfaceClass -> {
                            if (interfaces.contains(interfaceClass)) {
                                //Check if class contains annotations
                                assertThat(Arrays.stream(clazz.getAnnotations())
                                        .noneMatch(annotation ->
                                                annotation.annotationType() == Component.class ||
                                                        annotation.annotationType() == Service.class ||
                                                        annotation.annotationType() == Repository.class
                                        )).describedAs(clazz+ " is missing annotation (@Component/@Service/@Repository).").isFalse();
                            }
                        });


                if (clazz.getName().endsWith("Resource")) {
                    assertThat(Arrays.stream(clazz.getAnnotations())
                            .noneMatch(annotation ->
                                    annotation.annotationType() == RestController.class
                            )).describedAs(clazz+ " is missing annotation (@RestController).").isFalse();
                }
            }

        });
    }
}
