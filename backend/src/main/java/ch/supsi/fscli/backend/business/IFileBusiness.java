package ch.supsi.fscli.backend.business;

public interface IFileBusiness extends FSCommandsBusiness, IFSElementBusiness<FSElementBusiness> {
    FileBusinessBusiness touch(String name);
}
