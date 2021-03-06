package com.offcn.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.offcn.common.Md5Utils;
import com.offcn.common.PageResult;
import com.offcn.dao.TbSellerMapper;
import com.offcn.pojo.TbSeller;
import com.offcn.pojo.TbSellerExample;
import com.offcn.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private TbSellerMapper tbSellerMapper;


    /**
     * 查询全部
     */
    @Override
    public List<TbSeller> findAll() {
        return tbSellerMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbSeller> page=   (Page<TbSeller>) tbSellerMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbSeller seller) {
        tbSellerMapper.insert(seller);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbSeller seller){
       tbSellerMapper.updateByPrimaryKey(seller);
    }

    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    @Override
    public TbSeller findOne(String sellerId){
        return tbSellerMapper.selectByPrimaryKey(sellerId);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(String[] sellerIds) {
        for(String sellerId:sellerIds){
            tbSellerMapper.deleteByPrimaryKey(sellerId);
        }
    }


    @Override
    public PageResult findPage(TbSeller seller, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        seller.setStatus("0");
        TbSellerExample example=new TbSellerExample();
        TbSellerExample.Criteria criteria = example.createCriteria();

        if(seller!=null){
            if(seller.getName()!=null && seller.getName().length()>0){
                criteria.andNameLike("%"+seller.getName()+"%");
            }			if(seller.getNickName()!=null && seller.getNickName().length()>0){
                criteria.andNickNameLike("%"+seller.getNickName()+"%");
            }			if(seller.getPassword()!=null && seller.getPassword().length()>0){
                criteria.andPasswordLike("%"+seller.getPassword()+"%");
            }			if(seller.getEmail()!=null && seller.getEmail().length()>0){
                criteria.andEmailLike("%"+seller.getEmail()+"%");
            }			if(seller.getMobile()!=null && seller.getMobile().length()>0){
                criteria.andMobileLike("%"+seller.getMobile()+"%");
            }			if(seller.getTelephone()!=null && seller.getTelephone().length()>0){
                criteria.andTelephoneLike("%"+seller.getTelephone()+"%");
            }			if(seller.getStatus()!=null && seller.getStatus().length()>0){
                criteria.andStatusLike("%"+seller.getStatus()+"%");
            }			if(seller.getAddressDetail()!=null && seller.getAddressDetail().length()>0){
                criteria.andAddressDetailLike("%"+seller.getAddressDetail()+"%");
            }			if(seller.getLinkmanName()!=null && seller.getLinkmanName().length()>0){
                criteria.andLinkmanNameLike("%"+seller.getLinkmanName()+"%");
            }			if(seller.getLinkmanQq()!=null && seller.getLinkmanQq().length()>0){
                criteria.andLinkmanQqLike("%"+seller.getLinkmanQq()+"%");
            }			if(seller.getLinkmanMobile()!=null && seller.getLinkmanMobile().length()>0){
                criteria.andLinkmanMobileLike("%"+seller.getLinkmanMobile()+"%");
            }			if(seller.getLinkmanEmail()!=null && seller.getLinkmanEmail().length()>0){
                criteria.andLinkmanEmailLike("%"+seller.getLinkmanEmail()+"%");
            }			if(seller.getLicenseNumber()!=null && seller.getLicenseNumber().length()>0){
                criteria.andLicenseNumberLike("%"+seller.getLicenseNumber()+"%");
            }			if(seller.getTaxNumber()!=null && seller.getTaxNumber().length()>0){
                criteria.andTaxNumberLike("%"+seller.getTaxNumber()+"%");
            }			if(seller.getOrgNumber()!=null && seller.getOrgNumber().length()>0){
                criteria.andOrgNumberLike("%"+seller.getOrgNumber()+"%");
            }			if(seller.getLogoPic()!=null && seller.getLogoPic().length()>0){
                criteria.andLogoPicLike("%"+seller.getLogoPic()+"%");
            }			if(seller.getBrief()!=null && seller.getBrief().length()>0){
                criteria.andBriefLike("%"+seller.getBrief()+"%");
            }			if(seller.getLegalPerson()!=null && seller.getLegalPerson().length()>0){
                criteria.andLegalPersonLike("%"+seller.getLegalPerson()+"%");
            }			if(seller.getLegalPersonCardId()!=null && seller.getLegalPersonCardId().length()>0){
                criteria.andLegalPersonCardIdLike("%"+seller.getLegalPersonCardId()+"%");
            }			if(seller.getBankUser()!=null && seller.getBankUser().length()>0){
                criteria.andBankUserLike("%"+seller.getBankUser()+"%");
            }			if(seller.getBankName()!=null && seller.getBankName().length()>0){
                criteria.andBankNameLike("%"+seller.getBankName()+"%");
            }
        }

        Page<TbSeller> page= (Page<TbSeller>)tbSellerMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void register(TbSeller tbSeller) {
        //加密密码
        String pwd = Md5Utils.getPwd(tbSeller.getPassword());
        tbSeller.setPassword(pwd);
        tbSeller.setStatus("0");
        tbSellerMapper.insert(tbSeller);
    }

    @Override
    public void updateSeller(String sellerId, String status) {
        TbSeller tbSeller = tbSellerMapper.selectByPrimaryKey(sellerId);
        tbSeller.setStatus(status);
        tbSellerMapper.updateByPrimaryKey(tbSeller);
    }
}
