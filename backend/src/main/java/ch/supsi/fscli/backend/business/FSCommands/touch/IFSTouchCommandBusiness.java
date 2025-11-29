package ch.supsi.fscli.backend.business.FSCommands.touch;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSTouchCommandBusiness.class)
public interface IFSTouchCommandBusiness {
    boolean touch(String path) throws IllegalArgumentException;
}
