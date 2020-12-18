package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CodeService {
    @Autowired
    CodeRepository codeRepository;

    public void save(Code code) {
        codeRepository.save(code);

    }

    public List<Code> list() {
        return (List<Code>) codeRepository.findByIsSecretViewsFalseAndIsSecretTimeFalse();
    }

    public void delete(String id) {
        codeRepository.deleteById(id);

    }

    public Code findCodeById(String id) {
        return codeRepository.findById(id).get();
    }
}
