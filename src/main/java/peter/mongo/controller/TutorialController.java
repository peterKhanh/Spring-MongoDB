package peter.mongo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import peter.mongo.models.Tutorial;
import peter.mongo.repository.TutorialRepository;

@RestController
@RequestMapping("/api/v1")
public class TutorialController {

	@Autowired
	TutorialRepository repo;

	@GetMapping("/all")
	public ResponseEntity<List<Tutorial>> getAllTutorials() {
		return ResponseEntity.ok(repo.findAll());
	}

	@PostMapping("/add")
	public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
		try {
			Tutorial _tutorial = repo
					.insert(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished()));
			return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getbyid/{id}")
	public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") String id) {
		Optional<Tutorial> data = repo.findById(id);
		if (data.isPresent()) {
			return new ResponseEntity<>(data.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/deletebyid/{id}")
	public ResponseEntity<Tutorial> deleteTutorialById(@PathVariable("id") String id) {
		try {
			Optional<Tutorial> data = repo.findById(id);
			if (data.isPresent()) {
				repo.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteall")
	public ResponseEntity<Tutorial> deleteAllTutorial() {
		try {
			repo.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Tutorial> editTutorialById(@PathVariable("id") String id, @RequestBody Tutorial tutorial) {
		Optional<Tutorial> tutorialData = repo.findById(id);
		if (tutorialData.isPresent()) {
			Tutorial _tutorial = tutorialData.get();
			_tutorial.setTitle(tutorial.getTitle());
			_tutorial.setDescription(tutorial.getDescription());
			_tutorial.setPublished(_tutorial.isPublished());
			return new ResponseEntity<>(repo.save(_tutorial), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
