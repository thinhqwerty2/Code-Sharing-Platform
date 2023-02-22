package platform.model;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CodeService {
    private final CodeRepository codeRepository;


    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }


    public CodeSnippet getCodeById(String id) {
        return codeRepository.findById(id).orElse(null);
    }

    public List<CodeSnippet> get10LatestCode() {
        return codeRepository.get10LatestCode();
//
//        List<CodeSnippet> allCode = new ArrayList<>();
//        codeRepository.findAll().forEach(allCode::add);
//        List<CodeSnippet> rs = new ArrayList<>();
//        int count = 0;
//        for (int i = allCode.size() - 1; i >= 0; i--) {
//            if (allCode.get(i).getViews() != 0 || allCode.get(i).getTime() != 0) {
//                continue;
//            }
//            rs.add(allCode.get(i));
//            count++;
//            if (count == 10) break;
//        }
//        return rs;
    }

    //Return Id
    public String saveCode(CodeSnippet codeSnippet) {
        UUID uuid = UUID.randomUUID();
        String randomUUID = uuid.toString();
        codeSnippet.setId(randomUUID);
        codeSnippet.setCode(codeSnippet.getCode());
        codeSnippet.setDate(LocalDateTime.now());
        codeSnippet.setTime(codeSnippet.getTime() < 0 ? 0 : codeSnippet.getTime());
        codeSnippet.setViews(codeSnippet.getViews() < 0 ? 0 : codeSnippet.getViews());
        codeRepository.save(codeSnippet);
        return randomUUID;
    }

    public void updateCode(CodeSnippet codeSnippet) {
        codeSnippet.setCode(codeSnippet.getCode());
        codeSnippet.setTime(codeSnippet.getTime() < 0 ? 0 : codeSnippet.getTime());
        codeSnippet.setViews(codeSnippet.getViews() < 0 ? 0 : codeSnippet.getViews());
        codeRepository.save(codeSnippet);
    }

    public void deleteCode(CodeSnippet codeSnippet) {
        codeRepository.delete(codeSnippet);
    }

    public long countEntities() {
        return codeRepository.count();
    }

    public void update(CodeSnippet codeSnippet) {
        updateCode(codeSnippet);
    }
}

