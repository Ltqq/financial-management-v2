# 财务管理系统
## 主要技术: springboot + springsecurity + mysql + mybatis-plus + swagger +thymeleaf + jQuery + layuimini + Tablesaw 
## 描述:
根据登陆账户进行权限判断,返回不同菜单界面.
### 管理员:
有权管理所有员工,查看所有部门详细信息,管理公告,处理员工上报的账单.
### 部门主管:
查看本部门信息
### 普通员工:
查看本人信息
## 权限设计
根据角色判断权限:admin,部门主管,普通员工.
