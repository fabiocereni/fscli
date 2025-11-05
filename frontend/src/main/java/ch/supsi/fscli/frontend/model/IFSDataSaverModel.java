package ch.supsi.fscli.frontend.model;

import ch.supsi.fscli.backend.business.AbstractFSBusiness;

import java.nio.file.Path;

public interface IFSDataSaverModel {
    void save(Path path, AbstractFSBusiness abstractFSBusiness);
    void save(AbstractFSBusiness abstractFSBusiness);
}