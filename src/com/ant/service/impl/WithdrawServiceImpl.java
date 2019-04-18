package com.ant.service.impl;

import com.ant.dao.inte.BaseDaoI;
import com.ant.glob.GolbParams;
import com.ant.pojo.Account;
import com.ant.pojo.Notice;
import com.ant.pojo.User;
import com.ant.pojo.Withdraw;
import com.ant.service.inte.NoticeService;
import com.ant.service.inte.WithdrawService;
import com.ant.util.DateUtils;
import com.ant.util.StrUtils;
import com.ant.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
@Transactional
public class WithdrawServiceImpl implements WithdrawService {
    @Autowired
    private BaseDaoI dao;


    @Override
    public Map<String,Object> getWithdrawList(Map<String, Object> map, Integer page, Integer rows) {
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        StringBuffer orderQuerySql = new StringBuffer();
        orderQuerySql.append("select ");
        orderQuerySql.append("n.* ,u.userAccount ");
        orderQuerySql.append("from t_withdraw n ");
        orderQuerySql.append("left join t_user u on n.user_id = u.id ");
        orderQuerySql.append("where ");
        orderQuerySql.append("1 = 1");
        if(!StrUtils.isBlank((String)map.get("userAccount"))){
            orderQuerySql.append("and u.userAccount like :userAccount ");
            params.put("userAccount", "%"+map.get("userAccount")+"%");
        }
        if(!StrUtils.isBlank((String)map.get("state"))){
            orderQuerySql.append("and n.state = :state ");
            params.put("state", map.get("state"));
        }
        List<Map<String, Object>> orderList =  (List<Map<String, Object>>)dao.findBySql(
                orderQuerySql.toString(), params, page, rows);

        String countSql = "SELECT COUNT(id) FROM ( " + orderQuerySql.toString()
                + " )z";
        BigInteger count = dao.countBySql(countSql, params);

        returnMap.put("count", count);
        returnMap.put("withdrawList", orderList);
        return returnMap;
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from t_withdraw where id="+id;
        dao.executeSql(sql);
    }

    @Override
    public void update(Integer id, Integer state) {
        String hql = "from Withdraw where id=" + id;
        Withdraw withdraw = (Withdraw) dao.find(hql).get(0);
        if("1".equals(state)){
            withdraw.setState(1);
            dao.saveOrUpdate(withdraw);
        }else{
            hql = "from Account where userId=" + withdraw.getUser_id();
            Account account = (Account) dao.find(hql).get(0);
            Integer account_type = withdraw.getAccount_type();
            if(account_type == 0){
                BigDecimal balance = new BigDecimal(account.getPackageJ());
                BigDecimal amount = withdraw.getAmount();
                account.setPackageJ(Double.valueOf(balance.add(amount).toString()));
                dao.saveOrUpdate(account);
            }else{
                BigDecimal balance = new BigDecimal(account.getPackageD());
                BigDecimal amount = withdraw.getAmount();
                account.setPackageD(Double.valueOf(balance.add(amount).toString()));
                dao.saveOrUpdate(account);
            }
            withdraw.setState(2);
            dao.saveOrUpdate(withdraw);
        }
    }
}
