package az.company.emailverification.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
private final RegistrationService service;

@PostMapping
    public String register(@RequestBody RegistrationRequest request){

          return service.register(request);
 }

 @GetMapping(path = "confirm")
    public String    confirm (@RequestParam("token") String token){
    return service.confirmToken(token);
 }
}
