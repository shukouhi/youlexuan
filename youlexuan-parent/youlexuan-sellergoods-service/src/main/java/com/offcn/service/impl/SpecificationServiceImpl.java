package com.offcn.service.impl;

import java.util.List;
import java.util.Map;

import com.offcn.common.Specfication;
import com.offcn.dao.TbSpecificationOptionMapper;
import com.offcn.pojo.TbSpecificationOption;
import com.offcn.pojo.TbSpecificationOptionExample;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.offcn.dao.TbSpecificationMapper;
import com.offcn.pojo.TbSpecification;
import com.offcn.pojo.TbSpecificationExample;
import com.offcn.pojo.TbSpecificationExample.Criteria;
import com.offcn.service.SpecificationService;

import com.offcn.common.PageResult;
import org.springframework.stereotype.Service;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private TbSpecificationMapper specificationMapper;
    @Autowired
    private TbSpecificationOptionMapper tbSpecificationOptionMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbSpecification> findAll() {
        return specificationMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(Specfication specification) {
        //添加规格
        specificationMapper.insert(specification.getSpecification());
        //添加规格选项
        specification.getSpectionOptionList().forEach(
                specificationOption -> {
                    Long id = specification.getSpecification().getId();
                    specificationOption.setSpecId(id);
                    tbSpecificationOptionMapper.insert(specificationOption);
                }
        );
    }


    /**
     * 修改
     */
    @Override
    public void update(Specfication specification) {
        specificationMapper.updateByPrimaryKey(specification.getSpecification());
//删除规格选项
        TbSpecificationOptionExample tbSpecificationOptionExample = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = tbSpecificationOptionExample.createCriteria();
        criteria.andSpecIdEqualTo(specification.getSpecification().getId());
        tbSpecificationOptionMapper.deleteByExample(tbSpecificationOptionExample);
        //更新
        specification.getSpectionOptionList().forEach(
                tbSpecificationOption -> {
                    Long id = specification.getSpecification().getId();
                    tbSpecificationOption.setSpecId(id);
                    tbSpecificationOptionMapper.insert(tbSpecificationOption);
                }
        );

    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Specfication findOne(Long id) {
        Specfication specfication = new Specfication();
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        specfication.setSpecification(tbSpecification);

        TbSpecificationOptionExample tbSpecificationOptionExample = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = tbSpecificationOptionExample.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<TbSpecificationOption> tbSpecificationOptions = tbSpecificationOptionMapper.selectByExample(tbSpecificationOptionExample);
        specfication.setSpectionOptionList(tbSpecificationOptions);
        return specfication;
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            //删除规格
            specificationMapper.deleteByPrimaryKey(id);
            //删除规格选项
            TbSpecificationOptionExample tbSpecificationOptionExample = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = tbSpecificationOptionExample.createCriteria();
            criteria.andSpecIdEqualTo(id);
            tbSpecificationOptionMapper.deleteByExample(tbSpecificationOptionExample);
        }
    }


    @Override
    public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbSpecificationExample example = new TbSpecificationExample();
        Criteria criteria = example.createCriteria();

        if (specification != null) {
            if (specification.getSpecName() != null && specification.getSpecName().length() > 0) {
                criteria.andSpecNameLike("%" + specification.getSpecName() + "%");
            }
        }

        Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Map> selectSpecficationOption() {
        return specificationMapper.selectSpecficationOption();
    }
}
