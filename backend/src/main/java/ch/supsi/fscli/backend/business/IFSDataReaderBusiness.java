package ch.supsi.fscli.backend.business;

import com.google.inject.ImplementedBy;

import java.io.File;

@ImplementedBy(FSDataReaderBusiness.class)
public interface IFSDataReaderBusiness {
    void reader(File file);
}
