package org.tinygroup.springmerge;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.Mergeable;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.*;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * BeanDefinition合并构造者。主要将需要合并的bean定义合并到当前的bean定义中
 * 
 */
public class BeanDefinitionProxy extends GenericBeanDefinition implements BeanDefinition {

    /** */
    private static final long    serialVersionUID       = 1L;

    private final static Log     logger                    = LogFactory
                                                            .getLog(BeanDefinitionProxy.class);
    private final BeanDefinition mergedBeanDefinition;
    private final BeanDefinition currentBeanDefinition;

    private boolean              isMergedPropertyValues = false;

    private boolean              isBeanClassNameUpdated = false;                               // 是否beanClassNameUpdated变更

    public BeanDefinitionProxy(BeanDefinition mergedBeanDefinition,
                               BeanDefinition currentBeanDefinition) {
        super(currentBeanDefinition);
        String currentBeanClassName = currentBeanDefinition.getBeanClassName();
        String mergeBeanClassName = mergedBeanDefinition.getBeanClassName();
        //如果类名不一致
        if (!StringUtils.equals(currentBeanClassName, mergeBeanClassName)) {
            if (mergedBeanDefinition instanceof AbstractBeanDefinition
                && ((AbstractBeanDefinition) mergedBeanDefinition).hasBeanClass()) {
                //以merged声明的class为主
                setBeanClass(((AbstractBeanDefinition) mergedBeanDefinition).getBeanClass());
            } else {
                setBeanClass(null);//不让被覆盖类，被使用
            }
        }

        Assert.notNull(mergedBeanDefinition);
        Assert.notNull(currentBeanDefinition);
        this.mergedBeanDefinition = mergedBeanDefinition;
        this.currentBeanDefinition = currentBeanDefinition;

    }

    /**
     * 修复被引用时，引用者还是使用覆盖前BD的问题
     */
    public Class<?> getBeanClass() throws IllegalStateException {
        Object beanClassObject = super.getBeanClass();
        if (beanClassObject == null && mergedBeanDefinition instanceof AbstractBeanDefinition) {
            return ((AbstractBeanDefinition) mergedBeanDefinition).getBeanClass();
        }
        if (beanClassObject == null) {
            throw new IllegalStateException("No bean class specified on bean definition");
        }
        if (!(beanClassObject instanceof Class)) {
            throw new IllegalStateException("Bean class name [" + beanClassObject
                                            + "] has not been resolved into an actual Class");
        }
        return (Class) beanClassObject;
    }

    public String getBeanClassName() {
        String currentBeanClassName = currentBeanDefinition.getBeanClassName();

        String mergeBeanClassName = mergedBeanDefinition.getBeanClassName();
        if (mergeBeanClassName == null || currentBeanClassName.equals(mergeBeanClassName)) {
            return currentBeanClassName;
        }
        currentBeanDefinition.setBeanClassName(mergeBeanClassName);
        isBeanClassNameUpdated = true;
        if (logger.isInfoEnabled()) {
            logger.info("将原BeanClassName属性值:" + currentBeanClassName + "替换为" + mergeBeanClassName);
        }
        return mergeBeanClassName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.config.BeanDefinition#
     * getConstructorArgumentValues() 构造函数主要是通过替换形式
     */
    public ConstructorArgumentValues getConstructorArgumentValues() {
        if (isBeanClassNameUpdated) {// 如果class变的话，用变更后的属性定义
            return mergedBeanDefinition.getConstructorArgumentValues();
        }
        ConstructorArgumentValues mergedCAV = mergedBeanDefinition.getConstructorArgumentValues();
        ConstructorArgumentValues currentCAV = currentBeanDefinition.getConstructorArgumentValues();
        if (mergedCAV == null || mergedCAV.getArgumentCount() < 1 || mergedCAV.equals(currentCAV)) {
            return currentCAV;
        }
        currentBeanDefinition.getConstructorArgumentValues().clear();
        currentBeanDefinition.getConstructorArgumentValues().addArgumentValues(mergedCAV);
        if (logger.isInfoEnabled()) {
            logger.info("将原ConstructorArgumentValues属性值:" + currentCAV + "替换为" + mergedCAV);
        }
        return mergedCAV;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.config.BeanDefinition#getFactoryBeanName
     * ()
     */
    public String getFactoryBeanName() {

        if (isBeanClassNameUpdated) {// 如果class变的话，用变更后的属性定义
            return mergedBeanDefinition.getFactoryBeanName();
        }
        String mergedFactoryBeanName = mergedBeanDefinition.getFactoryBeanName();
        String currentFactoryBeanName = currentBeanDefinition.getFactoryBeanName();
        if (mergedFactoryBeanName == null || mergedFactoryBeanName.equals(currentFactoryBeanName)) {
            return currentFactoryBeanName;
        }
        currentBeanDefinition.setFactoryMethodName(mergedFactoryBeanName);
        if (logger.isInfoEnabled()) {
            logger.info("将原FactoryBeanName属性值:" + currentFactoryBeanName + "替换为"
                     + mergedFactoryBeanName);
        }
        return mergedFactoryBeanName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.config.BeanDefinition#getFactoryMethodName
     * ()
     */
    public String getFactoryMethodName() {
        if (isBeanClassNameUpdated) {// 如果class变的话，用变更后的属性定义
            return mergedBeanDefinition.getFactoryMethodName();
        }
        String mergedFactoryMethodName = mergedBeanDefinition.getFactoryMethodName();
        String currentFactoryMethodName = currentBeanDefinition.getFactoryMethodName();
        if (mergedFactoryMethodName == null
            || mergedFactoryMethodName.equals(currentFactoryMethodName)) {
            return currentFactoryMethodName;
        }
        currentBeanDefinition.setFactoryBeanName(mergedFactoryMethodName);
        if (logger.isInfoEnabled()) {
            logger.info("将原factoryMethodName属性值:" + currentFactoryMethodName + "替换为"
                     + mergedFactoryMethodName);
        }
        return mergedFactoryMethodName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.config.BeanDefinition#
     * getOriginatingBeanDefinition()
     */
    public BeanDefinition getOriginatingBeanDefinition() {
        // TO DO 暂时不替换
        return currentBeanDefinition.getOriginatingBeanDefinition();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.config.BeanDefinition#getParentName()
     */
    public String getParentName() {

        if (isBeanClassNameUpdated) {// 如果class变的话，用变更后的属性定义
            return mergedBeanDefinition.getParentName();
        }
        String mergedParentName = mergedBeanDefinition.getParentName();
        String currentParentName = currentBeanDefinition.getParentName();
        if (mergedParentName == null || mergedParentName.equals(currentParentName)) {
            return currentParentName;
        }

        currentBeanDefinition.setParentName(currentParentName);
        if (logger.isInfoEnabled()) {
            logger.info("将原parentName属性值:" + currentParentName + "替换为" + mergedParentName);
        }
        return mergedParentName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.config.BeanDefinition#getPropertyValues
     * ()
     */
    @SuppressWarnings("unchecked")
    public MutablePropertyValues getPropertyValues() {

        if (isBeanClassNameUpdated) {// 如果class变的话，用变更后的属性定义
            return mergedBeanDefinition.getPropertyValues();
        }
        MutablePropertyValues currentMpv = currentBeanDefinition.getPropertyValues();
        if (isMergedPropertyValues) {
            return currentMpv;
        }
        MutablePropertyValues mergedMpv = mergedBeanDefinition.getPropertyValues();
        if (currentMpv.equals(mergedMpv))// 如果相等则返回
        {
            return currentMpv;
        }
        PropertyValue[] mergedPvArray = mergedMpv.getPropertyValues();
        for (PropertyValue pv : mergedPvArray) {
            String mergedName = pv.getName();
            Object mergedValue = pv.getValue();
            PropertyValue currentPV = currentMpv.getPropertyValue(mergedName);
            if (currentPV == null)// 不存在直接新增
            {
                if (logger.isInfoEnabled()) {
                    logger.info("将需要merged(PropertyValues)(" + mergedName + ")value:" + mergedValue
                             + "直接新增到spring'" + this.getBeanClassName() + "'中");
                }
                // 兼容老的merge-inf机制中存在merge=true的情况
                if (mergedValue instanceof Mergeable && ((Mergeable) mergedValue).isMergeEnabled()) {
                    PropertyValue hintpv = currentMpv.getPropertyValue("merge");
                    if (hintpv != null) {
                        hintpv.setConvertedValue(Boolean.TRUE);
                    }
                }
                currentMpv.addPropertyValue(pv);
            } else// 存在的话，则作替换或合并
            {
                Object currentValue = currentPV.getValue();
                if (mergedValue.equals(currentValue)) {
                    continue;
                }
                currentPV.removeAttribute(mergedName);
                currentMpv.removePropertyValue(mergedName);
                if (mergedValue instanceof RuntimeBeanReference) {
                    currentMpv.addPropertyValue(mergedName, mergedValue);
                    if (logger.isInfoEnabled()) {
                        logger.info("将原属性值(PropertyValues)(" + mergedName + ")value:" + currentValue
                                 + "替换为" + mergedValue);
                    }
                } else if (mergedValue instanceof BeanDefinitionHolder) {
                    currentMpv.addPropertyValue(mergedName, mergedValue);
                    if (logger.isInfoEnabled()) {
                        logger.info("将原属性值(PropertyValues)(" + mergedName + ")value:" + currentValue
                                 + "替换为" + mergedValue);
                    }
                } else if (mergedValue instanceof ManagedSet) {

                    ManagedSet currentManagedSet = (ManagedSet) currentValue;
                    ManagedSet mergedManagedSet = (ManagedSet) mergedValue;
                    ManagedSet tempManagedSet = (ManagedSet) currentManagedSet.clone();
                    if (mergedManagedSet.isMergeEnabled()) {
                        currentManagedSet.addAll((ManagedSet) mergedValue);
                        if (logger.isInfoEnabled()) {
                            logger.info("将需要merged属性值(PropertyValues)(" + mergedName + ")value:"
                                     + mergedValue + "和现属性value" + tempManagedSet + "合并为"
                                     + currentManagedSet);
                        }
                    } else {
                        currentManagedSet = mergedManagedSet;
                        if (logger.isInfoEnabled()) {
                            logger.info("属性值(PropertyValues)(" + mergedName + ")由现属性value"
                                     + tempManagedSet + "直接替换为" + mergedValue);
                        }
                    }
                    currentMpv.addPropertyValue(mergedName, currentManagedSet);
                } else if (mergedValue instanceof ManagedList) {
                    ManagedList currentManagedList = (ManagedList) currentValue;
                    ManagedList managedListed = (ManagedList) currentManagedList.clone();
                    ManagedList mergeManagedList = (ManagedList) mergedValue;
                    if (mergeManagedList.isMergeEnabled()) {
                        currentManagedList.addAll((ManagedList) mergedValue);
                        if (logger.isInfoEnabled()) {
                            logger.info("将需要merged属性值(PropertyValues)(" + mergedName + ")value:"
                                     + mergedValue + "和现属性value" + managedListed + "合并为"
                                     + currentManagedList);
                        }
                    } else {
                        currentManagedList = mergeManagedList;
                        if (logger.isInfoEnabled()) {
                            logger.info("属性值(PropertyValues)(" + mergedName + ")由现属性value"
                                     + currentManagedList + "直接替换为" + mergedValue);
                        }
                    }
                    currentMpv.addPropertyValue(mergedName, currentManagedList);

                } else if (mergedValue instanceof ManagedMap) {
                    ManagedMap currentManagedMap = (ManagedMap) currentValue;
                    ManagedMap managedMap = (ManagedMap) currentManagedMap.clone();
                    ManagedMap mergeManagedMap = (ManagedMap) mergedValue;
                    if (mergeManagedMap.isMergeEnabled()) {
                        currentManagedMap.putAll((ManagedMap) mergedValue);
                        if (logger.isInfoEnabled()) {
                            logger.info("将需要merged属性值(PropertyValues)(" + mergedName + ")value:"
                                     + mergedValue + "和现属性value" + managedMap + "合并为"
                                     + currentManagedMap);
                        }

                    } else {
                        currentManagedMap = mergeManagedMap;
                        if (logger.isInfoEnabled()) {
                            logger.info("属性值(PropertyValues)(" + mergedName + ")由现属性value"
                                     + currentManagedMap + "直接替换为" + mergedValue);
                        }
                    }
                    currentMpv.addPropertyValue(mergedName, currentManagedMap);
                } else if (mergedValue instanceof ManagedProperties) {
                    ManagedProperties currentManageProperties = (ManagedProperties) currentValue;
                    ManagedProperties manageProperties = (ManagedProperties) currentManageProperties
                        .clone();
                    ManagedProperties mergedManageProperties = (ManagedProperties) mergedValue;
                    if (mergedManageProperties.isMergeEnabled()) {
                        currentManageProperties.merge(mergedValue);
                        if (logger.isInfoEnabled()) {
                            logger.info("将需要merged属性值(PropertyValues)(" + mergedName + ")value:"
                                     + mergedValue + "和现属性value" + manageProperties + "合并为"
                                     + currentManageProperties);
                        }

                    } else {
                        currentManageProperties = mergedManageProperties;
                        if (logger.isInfoEnabled()) {
                            logger.info("属性值(PropertyValues)(" + mergedName + ")由现属性value"
                                     + currentManageProperties + "直接替换为" + mergedValue);
                        }

                    }
                    currentMpv.addPropertyValue(mergedName, currentManageProperties);
                } else {
                    currentMpv.removePropertyValue(mergedName);
                    currentMpv.addPropertyValue(new PropertyValue(mergedName, mergedValue));
                }
            }

        }
        isMergedPropertyValues = true;
        return currentMpv;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.config.BeanDefinition#
     * getResourceDescription()
     */
    public String getResourceDescription() {

        String currentRdes = currentBeanDefinition.getResourceDescription();
        String mergedRdes = mergedBeanDefinition.getResourceDescription();

        return mergedRdes + "(需要合并)," + currentRdes + "(当前)";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.config.BeanDefinition#getRole()
     */
    public int getRole() {
        int mergeRole = mergedBeanDefinition.getRole();
        int currentRole = currentBeanDefinition.getRole();
        if (mergeRole == ROLE_APPLICATION || (mergeRole == currentRole)) {
            return currentRole;
        }
        if (logger.isInfoEnabled()) {
            logger.info("将原来role属性值:" + currentRole + "规换为" + currentRole);
        }
        return mergeRole;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.config.BeanDefinition#getScope()
     * scope由默认值。如果需要merged的scope是默认的，仍以当前的scope为主
     */
    public String getScope() {

        if (isBeanClassNameUpdated) {// 如果class变的话，用变更后的属性定义
            return mergedBeanDefinition.getScope();
        }

        String mergedScope = mergedBeanDefinition.getScope();
        String currentScope = currentBeanDefinition.getScope();
        if (currentScope == null) {
            currentScope = SCOPE_SINGLETON;
        }
        if (mergedScope == null || mergedScope.equals(SCOPE_SINGLETON)
            || mergedScope.equals(currentScope)) {
            return currentScope;
        }
        currentBeanDefinition.setScope(mergedScope);
        if (logger.isInfoEnabled()) {
            logger.info("将原scope属性值" + currentScope + "替换为" + mergedScope);
        }
        return mergedScope;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.config.BeanDefinition#isAbstract()
     * 此属性由于是默认值，难于merged。以当前的属性值为主
     */
    public boolean isAbstract() {
        if (isBeanClassNameUpdated) {// 如果class变的话，用变更后的属性定义
            return mergedBeanDefinition.isAbstract();
        }

        boolean mergedIsAbstract = mergedBeanDefinition.isAbstract();
        boolean currentIsAbstract = currentBeanDefinition.isAbstract();
        if (!mergedIsAbstract || (mergedIsAbstract == currentIsAbstract)) {

            return currentIsAbstract;
        }
        if (logger.isInfoEnabled()) {
            logger.info("将原isAbstract属性值" + currentIsAbstract + "替换为" + mergedIsAbstract);
        }
        return mergedIsAbstract;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.config.BeanDefinition#isAutowireCandidate
     * () isAutowireCandidate由默认值。如果需要merged的isAutowireCandidate是默认的，
     * 仍以当前的isAutowireCandidate为主
     */
    public boolean isAutowireCandidate() {

        if (isBeanClassNameUpdated) {// 如果class变的话，用变更后的属性定义
            return mergedBeanDefinition.isAutowireCandidate();
        }
        boolean mergedIsAutowireCandidate = mergedBeanDefinition.isAutowireCandidate();
        boolean currentIsAutowireCandidate = currentBeanDefinition.isAutowireCandidate();
        if (mergedIsAutowireCandidate || (mergedIsAutowireCandidate == currentIsAutowireCandidate)) {

            return currentIsAutowireCandidate;
        }
        if (logger.isInfoEnabled()) {
            logger.info("将原isAbstract属性值" + currentIsAutowireCandidate + "替换为"
                     + mergedIsAutowireCandidate);
        }
        return mergedIsAutowireCandidate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.config.BeanDefinition#isLazyInit()
     * 由于lazyInit存在默认属性，merge话存在一定复杂度。建议开发人员使用默认，不要随便更改
     */
    public boolean isLazyInit() {

        if (isBeanClassNameUpdated) {// 如果class变的话，用变更后的属性定义
            return mergedBeanDefinition.isLazyInit();
        }

        boolean mergedIsLazyInit = mergedBeanDefinition.isLazyInit();
        boolean currentIsLazyInit = currentBeanDefinition.isLazyInit();
        if (!mergedIsLazyInit || (mergedIsLazyInit == currentIsLazyInit)) {

            return currentIsLazyInit;
        }
        if (logger.isInfoEnabled()) {
            logger.info("将原isLazyInit属性值" + currentIsLazyInit + "替换为" + mergedIsLazyInit);
        }
        return mergedIsLazyInit;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.config.BeanDefinition#isSingleton()
     * 注：由于默认是single。如果当前值非single而需要merge的是single。这是仍以当前的scope为主。 to do
     * 需要严格测试和讨论，scope合并侧略
     */
    public boolean isSingleton() {

        if (isBeanClassNameUpdated) {// 如果class变的话，用变更后的属性定义
            return mergedBeanDefinition.isSingleton();
        }

        boolean mergedIsSingleton = mergedBeanDefinition.isSingleton();
        boolean currentIsSingleton = currentBeanDefinition.isSingleton();
        if (!mergedIsSingleton && mergedIsSingleton != currentIsSingleton) {
            if (logger.isInfoEnabled()) {
                logger.info("将原isSingleton属性值" + currentIsSingleton + "替换为" + mergedIsSingleton);
            }
            return mergedIsSingleton;
        }

        return currentIsSingleton;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.core.AttributeAccessor#attributeNames()
     */
    public String[] attributeNames() {

        if (isBeanClassNameUpdated) {// 如果class变的话，用变更后的属性定义
            return mergedBeanDefinition.attributeNames();
        }

        String[] mergedAttributesNames = mergedBeanDefinition.attributeNames();
        String[] currentAttributesNames = currentBeanDefinition.attributeNames();
        List<String> list = new ArrayList<String>();
        String[] newArray = (String[]) ArrayUtils.addAll(currentAttributesNames,
            currentAttributesNames);
        if (newArray.length < 1) {
            return new String[0];
        }
        for (String str : newArray) {
            if (list.contains(str)) {
                list.add(str);
            }
        }
        String[] mergedArray = null;
        if (list.size() > 0) {
            mergedArray = list.toArray(new String[list.size()]);
        } else {
            mergedArray = ArrayUtils.EMPTY_STRING_ARRAY;
        }
        if (logger.isInfoEnabled()) {
            logger.info("将attributeNames数组属性值" + StringUtils.join(mergedAttributesNames) + "和"
                     + StringUtils.join(currentAttributesNames) + "copy并去重为"
                     + StringUtils.join(mergedArray));
        }
        return mergedArray;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.core.AttributeAccessor#getAttribute(java.lang.String)
     */
    public Object getAttribute(String name) {
        if (isBeanClassNameUpdated) {// 如果class变的话，用变更后的属性定义
            return mergedBeanDefinition.getAttribute(name);
        }

        Object mergedAttribute = mergedBeanDefinition.getAttribute(name);
        Object currentAttribute = currentBeanDefinition.getAttribute(name);
        if (mergedAttribute == null || mergedAttribute.equals(currentAttribute)) {
            return currentAttribute;
        }
        currentBeanDefinition.setAttribute(name, mergedAttribute);
        if (logger.isInfoEnabled()) {
            logger.info("原attribute(" + name + ")属性值:" + mergedAttribute + "替换为" + currentAttribute);
        }
        return mergedAttribute;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.core.AttributeAccessor#hasAttribute(java.lang.String)
     */
    public boolean hasAttribute(String name) {
        if (isBeanClassNameUpdated) {// 如果class变的话，用变更后的属性定义
            return mergedBeanDefinition.hasAttribute(name);
        }
        boolean mergedhasAttribute = mergedBeanDefinition.hasAttribute(name);
        boolean currenthasAttribute = currentBeanDefinition.hasAttribute(name);
        if (currenthasAttribute || mergedhasAttribute) {
            if (logger.isInfoEnabled()) {
                logger.info("将原hasAttribute属性值" + currenthasAttribute + "替换为" + mergedhasAttribute);
            }
            return true;
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.core.AttributeAccessor#removeAttribute(java.lang.
     * String)
     */
    public Object removeAttribute(String name) {

        return currentBeanDefinition.removeAttribute(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.BeanMetadataElement#getSource()
     */
    public Object getSource() {
        if (isBeanClassNameUpdated) {// 如果class变的话，用变更后的属性定义
            return mergedBeanDefinition.getSource();
        }

        Object mergedSource = mergedBeanDefinition.getSource();
        Object currentSource = currentBeanDefinition.getSource();
        if (mergedSource == null || mergedSource.equals(currentSource)) {
            return currentSource;
        }
        if (logger.isInfoEnabled()) {
            logger.info("原source属性值" + currentSource + "替换为" + mergedSource);
        }
        return mergedSource;
    }

}
