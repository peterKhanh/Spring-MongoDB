package peter.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import peter.mongo.models.Tutorial;

public interface TutorialRepository extends MongoRepository<Tutorial, String> {

}
