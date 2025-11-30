package ch.supsi.fscli.frontend.model;


import com.google.inject.ImplementedBy;

import java.io.File;

@ImplementedBy(FSDataReaderModel.class)
public interface IFSDataReaderModel {
    void reader(File file);
}
