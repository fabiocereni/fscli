package ch.supsi.fscli.frontend.model;

import ch.supsi.fscli.backend.business.AbstractBusiness;

import java.nio.file.Path;

public interface IFSDataSaverModel {
    void save(Path path, AbstractBusiness abstractBusiness);
    void save(AbstractBusiness abstractBusiness);
}