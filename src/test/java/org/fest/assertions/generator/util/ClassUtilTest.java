package org.fest.assertions.generator.util;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.generator.util.ClassUtil.*;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import org.fest.assertions.generator.data.ArtWork;
import org.fest.assertions.generator.data.Movie;
import org.fest.assertions.generator.data.Name;
import org.fest.assertions.generator.data.Player;
import org.fest.assertions.generator.data.Race;
import org.fest.assertions.generator.data.Ring;

public class ClassUtilTest {

  private static final Class<?>[] NO_PARAMS = new Class[0];
  private Method method;

  @Test
  public void should_get_classes_in_package_and_subpackages() throws ClassNotFoundException {
    List<Class<?>> classesInPackage = getClassesInPackage("org.fest.assertions.generator.data");
    assertThat(classesInPackage).contains(Player.class, ArtWork.class, Name.class, Movie.class, Ring.class, Race.class);
  }

  @Test
  public void should_return_property_of_getter_method() throws Exception {
    method = Player.class.getMethod("getTeam", NO_PARAMS);
    assertThat(propertyNameOf(method)).isEqualTo("team");

    method = Player.class.getMethod("isRookie", NO_PARAMS);
    assertThat(propertyNameOf(method)).isEqualTo("rookie");
  }

  @Test
  public void should_return_true_if_class_implements_iterable_interface() {
    assertThat(isIterable(Iterable.class)).isTrue();
    assertThat(isIterable(Collection.class)).isTrue();
    assertThat(isIterable(List.class)).isTrue();
    assertThat(isIterable(String.class)).isFalse();
  }

  @Test
  public void should_return_true_if_method_is_a_standard_getter() throws Exception {
    method = Player.class.getMethod("getTeam", NO_PARAMS);
    assertThat(isStandardGetter(method)).isTrue();

    method = Player.class.getMethod("isRookie", NO_PARAMS);
    assertThat(isStandardGetter(method)).isFalse();
  }

  @Test
  public void should_return_true_if_method_is_a_boolean_getter() throws Exception {
    method = Player.class.getMethod("getTeam", NO_PARAMS);
    assertThat(isBooleanGetter(method)).isFalse();

    method = Player.class.getMethod("isRookie", NO_PARAMS);
    assertThat(isBooleanGetter(method)).isTrue();
  }

  @Test
  public void should_return_getters_methods_only() throws Exception {
    List<Method> playerGetterMethods = getterMethodsOf(Player.class);
    assertThat(playerGetterMethods).contains(Player.class.getMethod("getTeam", NO_PARAMS))
        .doesNotContain(Player.class.getMethod("isInTeam", String.class));
  }

}
