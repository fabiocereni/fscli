package ch.supsi.fscli.backend.business.filesystem.FSCommands.ln;

import com.google.inject.ImplementedBy;


@ImplementedBy(FSLnCommandBusiness.class)
public interface IFSLnCommandBusiness {
    boolean ln(String target, String linkName);

    String lns(String target, String linkName);
}
