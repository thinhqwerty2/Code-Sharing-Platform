package platform.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeRepository extends CrudRepository<CodeSnippet,String> {
    Optional<CodeSnippet> findById(String id);

    List<CodeSnippet> findTop10ByTimeAndViewsOrderByDateDesc(long time, long views);

    @Query(value = "SELECT * FROM CODE_SNIPPET  " +
            "        where time_restrict = 0 " +
            "          and view_restrict = 0 " +
            "     order by create_date desc" +
            "        LIMIT 10",
    nativeQuery = true)
    List<CodeSnippet> get10LatestCodes();

    @Query("SELECT c FROM CodeSnippet c WHERE c.time=0 AND c.views=0 ORDER BY c.date DESC")
    List<CodeSnippet> get10LatestCode();
}
