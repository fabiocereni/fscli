package ch.supsi.fscli.backend.application;

import com.google.inject.ImplementedBy;

import java.io.File;

@ImplementedBy(FSDataReaderApplication.class)
public interface IFSDataReaderApplication {
    void reader(File file);
}
