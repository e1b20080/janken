package oit.is.z1185.kaizi.janken.controller;

import java.util.ArrayList;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

//import oit.is.z1185.kaizi.janken.model.Janken;
import oit.is.z1185.kaizi.janken.model.MatchMapper;
import oit.is.z1185.kaizi.janken.model.User;
import oit.is.z1185.kaizi.janken.model.Match;
import oit.is.z1185.kaizi.janken.model.UserMapper;
//import oit.is.z1185.kaizi.janken.model.MatchInfo;
import oit.is.z1185.kaizi.janken.model.MatchInfoMapper;

@Controller
public class JankenController {

  @Autowired
  UserMapper user;

  @Autowired
  MatchMapper match;

  @Autowired
  MatchInfoMapper matchinfo;

  @GetMapping("/janken")
  // @Transactional
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    model.addAttribute("name", loginUser);
    ArrayList<User> users = user.selectAllUserName();
    ArrayList<Match> matches = match.selectAllmatches();
    model.addAttribute("users", users);
    model.addAttribute("matches", matches);
    return "janken.html";
  }

  @GetMapping("/match")
  public String match(Principal prin, @RequestParam Integer id, ModelMap model) {
    String loginUser = prin.getName();
    User match_user = user.selectById(id);
    model.addAttribute("user_id", id);
    model.addAttribute("user_name", loginUser);

    model.addAttribute("match_user", match_user);

    return "match.html";
  }

  // @GetMapping("/fight")
  // public String fight(Principal prin, @RequestParam Integer id, @RequestParam Integer hand, ModelMap model) {
  //   Janken janken = new Janken(hand);
  //   User match_user = user.selectById(id);
  //   User loginUser = user.selectByName(prin.getName());

  //   Match match_data = new Match(loginUser.getId(), match_user.getId(), janken.getMyhand(), janken.getCpuhand());
  //   match.insertMatches(match_data);

  //   model.addAttribute("user_id", id);
  //   model.addAttribute("user_name", prin.getName());
  //   model.addAttribute("match_user", match_user);

  //   model.addAttribute("my_hand", janken.getMyhand());
  //   model.addAttribute("cpu_hand", janken.getCpuhand());
  //   model.addAttribute("result", janken.Result());

  //   return "match.html";
  // }

  @GetMapping("/fight")
  public String fight(@RequestParam int id, @RequestParam String hand, Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    User user1 = user.selectByName(loginUser);
    matchinfo.insertMatchInfo(user1.getId(), id, hand, true);
    model.addAttribute("user", loginUser);
    return "wait.html";
  }
}
