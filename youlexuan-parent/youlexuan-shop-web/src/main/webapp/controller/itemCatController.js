//商品类目控制层
app.controller('itemCatController', function ($scope, $controller, itemCatService, typeTemplateService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        itemCatService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        itemCatService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        itemCatService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = itemCatService.update($scope.entity); //修改
        } else {
            $scope.entity.parentId = $scope.searchEntity.parentId;
            serviceObject = itemCatService.add($scope.entity);//增加
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
        itemCatService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchEntity = {parentId: 0};//定义搜索对象

    //搜索 增加parentId
    $scope.search = function (page, rows) {
        itemCatService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //根据父id查找类别
    $scope.findByParentId = function (parentId) {
        itemCatService.findByParentId(parentId).success(
            function (response) {
                $scope.list = response;
            }
        )
    }

    //设置grade层级
    $scope.grade = 1;
    $scope.setGrade = function (value) {
        $scope.grade = value;
    }

    //面包屑以及显示分类
    $scope.itemCatList = function (cat) {
        if ($scope.grade == 1) {
            $scope.entity2 = null;
            $scope.entity3 = null;
        }
        if ($scope.grade == 2) {
            $scope.entity2 = cat;
            $scope.entity3 = null;
        }
        if ($scope.grade == 3) {
            $scope.entity3 = cat;
        }
        $scope.searchEntity = {parentId: cat.id};
        $scope.reloadList();
    }


    // 添加分类，选择模板
    $scope.selectTypeList = function () {
        typeTemplateService.selectTypeList().success(
            function (response) {
                $scope.typelateList = {data: response}
            }
        )
    }

    $scope.deleteType = function () {
        itemCatService.deletype($scope.selectIds).success(
            function (response) {
                if(response.success){
                    window.location.reload();
                }else{
                    alert(response.msg)
                }
            });
    }
});	