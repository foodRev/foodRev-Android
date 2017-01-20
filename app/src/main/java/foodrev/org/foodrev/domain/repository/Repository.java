package foodrev.org.foodrev.domain.repository;

import foodrev.org.foodrev.domain.models.SampleModel;

public interface Repository {

    boolean insert(SampleModel model);

    boolean update(SampleModel model);

    SampleModel get(Object id);

    boolean delete(SampleModel model);
}
