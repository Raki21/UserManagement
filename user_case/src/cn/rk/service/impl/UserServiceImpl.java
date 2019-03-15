package cn.rk.service.impl;

import cn.rk.dao.UserDao;
import cn.rk.dao.impl.UserDaoImpl;
import cn.rk.domain.PageBean;
import cn.rk.domain.User;
import cn.rk.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao dao = new UserDaoImpl();
    @Override
    public List<User> findAll() {
        //调用DAO来完成查询
        return dao.findAll();
    }

    @Override
    public void deleteUser(String id) {
        if(id != null && !id.equals("")) {
            dao.deleteUser(Integer.parseInt(id));
        }
    }

    @Override
    public void updateUser(User user) {
        dao.updateUser(user);
    }

    @Override
    public void addUser(User user) {
        dao.addUser(user);
    }

    @Override
    public User login(User user) {
        return dao.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public User findUserById(String id) {
        if(id != null && !id.equals("")) {
            return  dao.findById(Integer.parseInt(id));
        }
        return null;
    }

    @Override
    public void delSelectedUser(String[] ids) {
        if(ids != null && ids.length>0) {
            //遍历数组
            for (String id : ids) {
                dao.deleteUser(Integer.parseInt(id));
            }
        }
    }

    @Override
    public PageBean<User> findUserByPage(String _curruntPage, String _rows) {

            //数据转换
            int curruntPage = Integer.parseInt(_curruntPage);
            int rows = Integer.parseInt(_rows);
            if(curruntPage<=0)
            {
                curruntPage=1;
            }

            //1 创建空的PageBean对象
            PageBean<User> pb = new PageBean<>();
            //2 设置参数
            pb.setCurrentPage(curruntPage);
            pb.setRows(rows);

            //3 调用dao查询totalCount
            int totalCount = dao.findTotalCount();
            pb.setTotalCount(totalCount);
            //4 调用dao查询List集合
            int start = (curruntPage - 1) * rows;
            List<User> list = dao.findByPage(start, rows);
            pb.setList(list);

            //5 计算总页码
            int totalPage = totalCount % rows == 0 ? (totalCount / rows) : (totalCount / rows) + 1;
            pb.setTotalPage(totalPage);


            return pb;

    }
}
