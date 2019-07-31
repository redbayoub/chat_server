package com.pro0inter.chatserver.model.groups;

import com.pro0inter.chatserver.model.user.User;
import com.pro0inter.chatserver.model.user.UserRepository;
import com.pro0inter.chatserver.model.user.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/groups")
public class GroupResource {
    @Autowired
    GroupRepository groupRepo;
    @Autowired
    UserResource userResource;
    @Autowired
    UserRepository userRepo;

    @PersistenceContext
    EntityManager entityManager;

    @PostMapping
    public Group add_group(@Valid @RequestBody Group tmp_group){
        if(tmp_group.getName()==null ||tmp_group.getName().isEmpty()||tmp_group.getMembers()==null || tmp_group.getMembers().size()<3) return new Group();
        List<Integer> ids=new ArrayList<>();
        tmp_group.getMembers().forEach(user -> ids.add(user.getId()));
        List<User> members= userRepo.findAllById(ids);
        Group new_group=new Group();
        new_group.setName(tmp_group.getName());

        new_group.getMembers().addAll(members);
        members.forEach(user -> user.getGroups().add(new_group));
        Group ret_group=groupRepo.save(new_group);
        userRepo.saveAll(members);
        return  ret_group;
    }

    @GetMapping("/find/{mem_id}")
    public List<Group> find_by_member(@PathVariable("mem_id") int mem_id){
        /*return entityManager.createNativeQuery("SELECT gr.group_id,gr.name FROM groups as gr " +
                "INNER JOIN (" +
                "SELECT groups_members.groups_group_id FROM groups_members WHERE groups_members.members_id=:mem_id" +
                ") as gm " +
                "ON gr.group_id=gm.groups_group_id", Group.class)
                .setParameter("mem_id",mem_id)
                .getResultList();*/
        return userResource.getUser(mem_id).getGroups();
    }
    @GetMapping("/{group_id}")
    public Group get(@PathVariable("group_id") int group_id){
        return groupRepo.findById(group_id).orElse(new Group());
    }
}
