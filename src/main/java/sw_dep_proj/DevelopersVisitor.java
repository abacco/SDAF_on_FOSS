package sw_dep_proj;

import org.repodriller.domain.Commit;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

// This class represents a RepoDriller Processor
// Implement CommitVisitor interface. 
// Inside of process(), we print the commit hash and the name of the developer.
public class DevelopersVisitor implements CommitVisitor{

    @Override
    public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
        writer.write(
            commit.getHash(),
            commit.getCommitter().getName()
        );
    }
    
}
