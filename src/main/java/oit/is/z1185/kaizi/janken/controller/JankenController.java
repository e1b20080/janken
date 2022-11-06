package oit.is.z1185.kaizi.janken.controller;

import java.util.ArrayList;

import java.security.Principal;

import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;

import oit.is.z1185.kaizi.janken.model.Janken;
import oit.is.z1185.kaizi.janken.model.MatchMapper;
import oit.is.z1185.kaizi.janken.model.User;
import oit.is.z1185.kaizi.janken.model.Match;
import oit.is.z1185.kaizi.janken.model.UserMapper;

@Controller
public class JankenController {

  // @Autowired
  // private Entry entry;

  @Autowired
  UserMapper user;

  @Autowired
  MatchMapper match;

  /*
   * @GetMapping("/janken")
   * public String janken() {
   * return "janken.html";
   * }
   */

  @GetMapping("/janken")
  // @Transactional
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    // this.entry.addUser(loginUser);
    // model.addAttribute("entry", this.entry);
    model.addAttribute("name", loginUser);
    ArrayList<User> users = user.selectAllUserName();
    ArrayList<Match> matches = match.selectAllmatches();
    model.addAttribute("users", users);
    model.addAttribute("matches", matches);
    return "janken.html";
  }

  // @PostMapping("/janken")
  // public String janken(@RequestParam String namae, ModelMap model) {
  // model.addAttribute("name", namae);
  // return "janken.html";
  // }

  @GetMapping("/match")
  public String match(Principal prin, @RequestParam Integer id, ModelMap model) {
    String loginUser = prin.getName();
    User match_user = user.selectById(id);
    model.addAttribute("user_id", id);
    model.addAttribute("user_name", loginUser);

    model.addAttribute("match_user", match_user);

    return "match.html";
  }

  /**
   * jankenの結果を返す
   *
   * @param hand
   * @param model
   * @return
   */
  @GetMapping("/fight")
  public String fight(Principal prin, @RequestParam Integer id, @RequestParam Integer hand, ModelMap model) {
    Janken janken = new Janken(hand);
    User match_user = user.selectById(id);
    User loginUser = user.selectByName(prin.getName());

    Match match_data = new Match(loginUser.getId(), match_user.getId(), janken.getMyhand(), janken.getCpuhand());
    match.insertMatches(match_data);

    model.addAttribute("user_id", id);
    model.addAttribute("user_name", prin.getName());
    model.addAttribute("match_user", match_user);

    model.addAttribute("my_hand", janken.getMyhand());
    model.addAttribute("cpu_hand", janken.getCpuhand());
    model.addAttribute("result", janken.Result());

    return "match.html";
  }

}
