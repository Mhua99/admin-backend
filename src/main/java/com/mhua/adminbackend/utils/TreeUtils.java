package com.mhua.adminbackend.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TreeUtils {

    /**
     * 递归构建树形结构
     * @param allNodes 所有节点列表
     * @param parentId 当前父节点ID（为null时表示根节点）
     * @param getIdFunc 获取节点ID的函数
     * @param getParentIdFunc 获取父节点ID的函数
     * @param setChildrenFunc 设置子节点列表的函数
     * @param <T> 节点类型
     * @param <ID> ID类型
     * @return 构建好的树形结构列表
     */
    public static <T, ID> List<T> buildTree(
            List<T> allNodes,
            ID parentId,
            Function<T, ID> getIdFunc,
            Function<T, ID> getParentIdFunc,
            BiConsumer<T, List<T>> setChildrenFunc) {

        return allNodes.stream()
                .filter(node -> Objects.equals(getParentIdFunc.apply(node), parentId))
                .peek(node -> {
                    // 递归查找子节点
                    List<T> children = buildTree(allNodes, getIdFunc.apply(node),
                            getIdFunc, getParentIdFunc, setChildrenFunc);
                    setChildrenFunc.accept(node, children);
                })
                .collect(Collectors.toList());
    }
}
