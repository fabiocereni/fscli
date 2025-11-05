package ch.supsi.fscli.backend.business;

public interface IDirectoryBusiness extends FSCommandsBusiness, IFSElementBusiness<FSElementBusiness> {
    DirectoryBusiness mkdir();
}
