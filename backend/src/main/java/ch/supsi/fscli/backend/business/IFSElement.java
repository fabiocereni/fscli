package ch.supsi.fscli.backend.business;

import java.util.*;

public interface IFSElement<T> {
    default T getParent() {return null;};
    default void setParent(T parent){};
    default String getName() {return null;};
    default void setName(String name) {};
    default List<T> getContent() {return null;};
    default void setCont(List<T> cont) {};
}
