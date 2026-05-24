package de.binaerraum.queneau.app

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

// Einfache Controller-Route für die benutzerdefinierte Login-Seite
@Controller
class LoginController {

    @GetMapping("/login")
    fun login(): String = "login"

}

