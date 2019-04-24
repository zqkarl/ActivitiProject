package com.activiti.controller;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.activiti.exceptions.GroupCouldNotFoundException;
import com.activiti.exceptions.MembershipHasExistException;
import com.activiti.exceptions.UserCouldNotFoundException;
import com.activiti.exceptions.UserExistedException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="crm")
@Api(value = "权限中心专用接口", description="权限中心专用接口")
public class CrmController {

	@Autowired
	private IdentityService identityService;
	
	@ApiOperation(value = "新增用户", notes = "新增用户")
	@ResponseStatus(value = HttpStatus.CREATED, reason="success created")
	@RequestMapping(value="identity/users", method=RequestMethod.POST)
	public Object createUser(
			@RequestParam(value="user_id") String userid,
			@RequestParam(value = "name", required=false) String name,
			@RequestParam(value = "email", required=false, defaultValue="defaultemail@abc.com") String email,
			@RequestParam(value="system") String system) {
		String id = userid + "_" + system;
		User user = identityService.createUserQuery().userId(id).singleResult();
		if (user != null) {
			throw new UserExistedException();
		}
		user = identityService.newUser(id);
		user.setFirstName(name);
		user.setLastName(system);
		user.setPassword(DigestUtils.md5Hex(userid+system));
		user.setEmail(email);
		identityService.saveUser(user);
		return 1;
	}
	
	@ApiOperation(value = "更新用户", notes = "更新用户")
	@RequestMapping(value="identity/users/{user_id}", method=RequestMethod.PUT)
	public String updateUser(@PathVariable(value="user_id") String userid,
			@RequestParam(value = "name", required=false) String name,
			@RequestParam(value = "email", required=false) String email,
			@RequestParam(value="system") String system) {
		userid = userid + "_" + system;
		User user = identityService.createUserQuery().userId(userid).singleResult();
		user.setFirstName(name);
		user.setLastName(system);
		user.setEmail(email);
		identityService.saveUser(user);
		return "1";
	}
	
	@ApiOperation(value = "删除用户", notes = "删除用户")
	@RequestMapping(value="identity/users/{userId}", method=RequestMethod.DELETE)
//	@ResponseStatus(value=HttpStatus.NO_CONTENT, reason="successfully deleted")
	public Object deleteUser(@PathVariable(value="userId") String userId,
			@RequestParam(value="system") String system) {
		User user = identityService.createUserQuery().userId(userId+"_"+system).singleResult();
		if (user == null) {
			return 0;
//			throw new UserCouldNotFoundException();
		}
		identityService.deleteUser(userId+"_"+system);
		return 1;
	}
	
	@ApiOperation(value = "新增群组", notes = "新增群组")
	@ResponseStatus(value = HttpStatus.CREATED, reason="success created")
	@RequestMapping(value = "identity/groups", method=RequestMethod.POST)
	public Object createGroup(@RequestParam(value="group_id") String groupId,
			@RequestParam(value="name") String name,
			@RequestParam(value="type", required=false) String type,
			@RequestParam(value="system") String system) {
		groupId = groupId+"_"+system;
		Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
		if (group != null) {
			throw new UserExistedException();
		}
		group = identityService.newGroup(groupId);
		group.setName(name);
		group.setType(type);
		identityService.saveGroup(group);
		return 1;
	}
	
	@ApiOperation(value = "更新群组", notes = "更新群组")
	@RequestMapping(value = "identity/groups/{groupId}", method=RequestMethod.PUT)
	public Object updateGroup(@PathVariable(value="groupId") String groupId,
			@RequestParam(value="name") String name,
			@RequestParam(value="type", required=false) String type,
			@RequestParam(value="system") String system) {
		groupId = groupId+"_"+system;
		Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
		if (group == null) {
			throw new GroupCouldNotFoundException();
		}
		group.setName(name);
		group.setType(type);
		identityService.saveGroup(group);
		return 1;
	}
	
	@ApiOperation(value = "删除群组", notes = "删除群组")
	@RequestMapping(value="identity/groups/{groupId}", method=RequestMethod.DELETE)
//	@ResponseStatus(value=HttpStatus.NO_CONTENT, reason="successfully deleted")
	public Object deleteGroup(@PathVariable(value="groupId") String groupId,
			@RequestParam(value="system") String system) {
		groupId = groupId+"_"+system;
		Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
		if (group == null) {
			return 0;
//			throw new GroupCouldNotFoundException();
		}
		identityService.deleteGroup(groupId);
		return 1;
	}
	
	@ApiOperation(value="为群组添加一个成员", notes="为群组添加一个成员")
	@ResponseStatus(value = HttpStatus.CREATED, reason="successfully binded")
	@RequestMapping(value="identity/groups/{groupId}/members", method=RequestMethod.POST)
	public Object serMembers(@PathVariable(value="groupId") String groupId,
			@RequestParam(value="userId") String userId,
			@RequestParam(value="system") String system) {
		userId = userId + "_" +system;
		groupId = groupId+"_"+system;
		Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
		if (group == null) {
			throw new GroupCouldNotFoundException();
		}
		// 查询用户
		User user = identityService.createUserQuery().userId(userId).memberOfGroup(groupId).singleResult();
		if (user != null) {
			throw new MembershipHasExistException();
		}
		identityService.createMembership(userId, groupId);
		return 1;
	}
	
	@ApiOperation(value="删除群组成员", notes="删除群组成员")
	@RequestMapping(value="identity/groups/{groupId}/members/{userId}", method=RequestMethod.DELETE)
	public Object deleteMembers(@PathVariable(value="groupId") String groupId,
			@PathVariable(value="userId") String userId,
			@RequestParam(value="system") String system) {
		userId = userId+"_"+system;
		groupId = groupId+"_"+system;
		Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
		if (group == null) {
			return 0;
//			throw new GroupCouldNotFoundException();
		}
		// 查询用户
		User user = identityService.createUserQuery().userId(userId).memberOfGroup(groupId).singleResult();
		if (user == null) {
			return 0;
		}
		identityService.deleteMembership(userId, groupId);
		return 1;
	}
}
