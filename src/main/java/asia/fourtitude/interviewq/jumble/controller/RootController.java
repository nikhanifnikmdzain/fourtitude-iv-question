package asia.fourtitude.interviewq.jumble.controller;

import java.time.ZonedDateTime;
import java.util.Collection;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import asia.fourtitude.interviewq.jumble.core.JumbleEngine;
import asia.fourtitude.interviewq.jumble.model.ExistsForm;
import asia.fourtitude.interviewq.jumble.model.PrefixForm;
import asia.fourtitude.interviewq.jumble.model.ScrambleForm;
import asia.fourtitude.interviewq.jumble.model.SearchForm;
import asia.fourtitude.interviewq.jumble.model.SubWordsForm;

@Controller
@RequestMapping(path = "/")
public class RootController {

    private static final Logger LOG = LoggerFactory.getLogger(RootController.class);

    private final JumbleEngine jumbleEngine;

    @Autowired(required = true)
    public RootController(JumbleEngine jumbleEngine) {
        this.jumbleEngine = jumbleEngine;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("timeNow", ZonedDateTime.now());
        return "index";
    }

    @GetMapping("scramble")
    public String doGetScramble(Model model) {
        model.addAttribute("form", new ScrambleForm());
        return "scramble";
    }

    @PostMapping("scramble")
    public String doPostScramble(
            @Valid @ModelAttribute(name = "form") ScrambleForm form,
            BindingResult bindingResult, Model model) {
        /*
         * TODO:
         * a) Validate the input `form`
         * b) To call JumbleEngine#scramble()
         * c) Presentation page to show the result
         * d) Must pass the corresponding unit tests
         */
    	
    	   if (bindingResult.hasErrors()) {
               return "scramble";
           }

           String scrambledText = jumbleEngine.scramble(form.getWord());

           form.setScramble(scrambledText);
        return "scramble";
    }

    @GetMapping("palindrome")
    public String doGetPalindrome(Model model) {
        model.addAttribute("words", this.jumbleEngine.retrievePalindromeWords());
        return "palindrome";
    }

    @GetMapping("exists")
    public String doGetExists(Model model) {
        model.addAttribute("form", new ExistsForm());
        return "exists";
    }

    @PostMapping("exists")
    public String doPostExists(
            @Valid @ModelAttribute(name = "form") ExistsForm form,
            BindingResult bindingResult, Model model) {
        /*
         * TODO:
         * a) Validate the input `form`
         * b) To call JumbleEngine#exists()
         * c) Presentation page to show the result
         * d) Must pass the corresponding unit tests
         */
    	  if (bindingResult.hasErrors()) {
              return "exists";
          }

    	  String trimmedWord = form.getWord().trim();
          boolean exists = jumbleEngine.exists(trimmedWord);

          form.setExists(exists);
          
          if (exists) {
        	  form.setWord(form.getWord());
          } else {
        	  form.setWord(form.getWord());
          }
          
		return "exists";
    }

    @GetMapping("prefix")
    public String doGetPrefix(Model model) {
        model.addAttribute("form", new PrefixForm());
        return "prefix";
    }

    @PostMapping("prefix")
    public String doPostPrefix(
            @Valid @ModelAttribute(name = "form") PrefixForm form,
            BindingResult bindingResult, Model model) {
        /*
         * TODO:
         * a) Validate the input `form`
         * b) To call JumbleEngine#wordsMatchingPrefix()
         * c) Presentation page to show the result
         * d) Must pass the corresponding unit tests
         */
    	
    	  if (bindingResult.hasErrors()) {
              return "prefix";
          }
    	  
    	  String trimmedWord = form.getPrefix().trim();
    	  Collection<String> words = jumbleEngine.wordsMatchingPrefix(trimmedWord);

          form.setWords(words);

        return "prefix";
    }

    @GetMapping("search")
    public String doGetSearch(Model model) {
        model.addAttribute("form", new SearchForm());
        return "search";
    }

    @PostMapping("search")
    public String doPostSearch(
            @Valid @ModelAttribute(name = "form") SearchForm form,
            BindingResult bindingResult, Model model) {
        /*
         * TODO:
         * a) Validate the input `form`
         * b) Show the fields error accordingly: "Invalid startChar", "Invalid endChar", "Invalid length".
         * c) To call JumbleEngine#searchWords()
         * d) Presentation page to show the result
         * e) Must pass the corresponding unit tests
         */
    	if (bindingResult.hasFieldErrors("length")) {
    		FieldError lengthError = bindingResult.getFieldError("length");
    		model.addAttribute("length",lengthError);
    		return "search";
    	}
    	if ((form.getStartChar() == null || form.getStartChar().isEmpty()) && 
   			 (form.getEndChar() == null || form.getEndChar().isEmpty()) &&
   			 (form.getLength() == null || form.getLength() <= 0)) {
            bindingResult.addError(new FieldError("form", "startChar", "Invalid startChar"));
            bindingResult.addError(new FieldError("form", "endChar", "Invalid endChar"));
            bindingResult.addError(new FieldError("form", "length", "Invalid length"));

        }
    	 if (bindingResult.hasErrors()) {
    		 
	        if (bindingResult.hasFieldErrors("startChar")) {
	            FieldError startCharError = bindingResult.getFieldError("startChar");
	            model.addAttribute("startChar",startCharError);
	        }
	        if (bindingResult.hasFieldErrors("endChar")) {
	            FieldError endCharError = bindingResult.getFieldError("endChar");
	            model.addAttribute("endChar",endCharError);
	        }
	        return "search";
	    }
         
         
    	 char startChar = form.getStartChar() != null && !form.getStartChar().isEmpty() ? form.getStartChar().charAt(0) : '\0';
         char endChar = form.getEndChar() != null && !form.getEndChar().isEmpty() ? form.getEndChar().charAt(0) : '\0';
         Integer length =  form.getLength() != null && form.getLength() !=0 ?  form.getLength() : 1;;

         Collection<String> words = jumbleEngine.searchWords(startChar, endChar, length);
         form.setWords(words);

        return "search";
    }

    @GetMapping("subWords")
    public String goGetSubWords(Model model) {
        model.addAttribute("form", new SubWordsForm());
        return "subWords";
    }

    @PostMapping("subWords")
    public String doPostSubWords(
            @Valid @ModelAttribute(name = "form") SubWordsForm form,
            BindingResult bindingResult, Model model) {
        /*
         * TODO:
         * a) Validate the input `form`
         * b) To call JumbleEngine#generateSubWords()
         * c) Presentation page to show the result
         * d) Must pass the corresponding unit tests
         */
    	
    	 if (bindingResult.hasErrors()) {

             if (bindingResult.hasFieldErrors("word")) {
            	 form.setWord("Invalid word");
             }
             if (bindingResult.hasFieldErrors("minLength")) {
                 model.addAttribute("minLength", "Invalid minLength");
             }
             return "subWords";
         }

    	 
    	 String word = form.getWord().trim();
         Integer minLength = form.getMinLength() != null && form.getMinLength() > 0 ? form.getMinLength() : 3;

         // Call JumbleEngine#generateSubWords() with the validated input
         Collection<String> words = jumbleEngine.generateSubWords(word, minLength);
         form.setWords(words);
        return "subWords";
    }

}
