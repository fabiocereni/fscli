package ch.supsi.fscli.backend.business.filesystem.FSCommands.touch;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSTouchCommandBusiness.class)
public interface IFSTouchCommandBusiness {
    String touch(String path) throws IllegalArgumentException;
}
