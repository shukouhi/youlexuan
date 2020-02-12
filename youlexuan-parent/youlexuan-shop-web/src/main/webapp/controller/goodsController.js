//控制层
app.controller('goodsController', function ($scope, $controller, goodsService,
                                            itemCatService, typeTemplateService, fileUpload) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        goodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        goodsService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        goodsService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = goodsService.update($scope.entity); //修改
        } else {
            serviceObject = goodsService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    //重新查询
                    $scope.reloadList();//重新加载
                } else {
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        goodsService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        goodsService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

//获取一级分类
    $scope.selectItemCatOneList = function () {
        itemCatService.findByParentId(0).success(
            function (response) {
                $scope.itemCatOneList = response;
            }
        )
    }

    //获取二级分类
    $scope.$watch('entity.tbGoods.category1Id', function (oldValue, newValue) {
        if (oldValue) {
            itemCatService.findByParentId(oldValue).success(
                function (response) {
                    $scope.itemCatTwoList = response;
                }
            )
        }
    })

    //获取三级分类
    $scope.$watch('entity.tbGoods.category2Id', function (oldValue, newValue) {
        if (oldValue) {
            itemCatService.findByParentId(oldValue).success(
                function (response) {
                    $scope.itemCatThreeList = response;
                }
            )
        }
    })

    //获取模板id
    $scope.$watch('entity.tbGoods.category3Id', function (oldValue, newValue) {
        if (oldValue) {
            itemCatService.findOne(oldValue).success(
                function (response) {
                    $scope.entity.tbGoods.typeTemplateId = response.typeId;
                }
            )
        }
    })

    //根据模板id查询品牌 扩展属性
    $scope.$watch('entity.tbGoods.typeTemplateId', function (oldValue, newValue) {
        if (oldValue) {
            typeTemplateService.findOne(oldValue).success(
                function (response) {
                    $scope.brandList = JSON.parse(response.brandIds);
                    $scope.entity.tbGoodsDesc.customAttributeItems = JSON.parse(response.customAttributeItems);
                }
            )
            //查询规格
            typeTemplateService.selectSpecList(oldValue).success(
                function (response) {
                    $scope.specList = response;

                }
            )
        }
    })

    //定义集合存放图片属性
    $scope.entity = {tbGoodsDesc: {itemImages: [], specificationItems: []}};
    //把选中规格选项装入集合
    $scope.updateSelectValue = function ($event, name, value) {''
        var obj = $scope.findValueByKey($scope.entity.tbGoodsDesc.specificationItems, "attributeName", name);
        if (obj != null) {

        }
    }


    //图片上传
    $scope.fileUpload = function () {
        fileUpload.fileUpload().success(
            function (response) {
                if (response.success) {
                    $scope.entity_image.url = response.msg;
                } else {
                    alert(response.msg);
                }
            }
        )
    }


    //将图片信息放到集合中
    $scope.addImage = function () {
        $scope.entity.tbGoodsDesc.itemImages.push($scope.entity_image);
    }
    //将图片从集合中移走
    $scope.deleteImage = function (index) {
        $scope.entity.tbGoodsDesc.itemImages.splice(index, 1);
    }
});	