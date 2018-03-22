package onememanyus.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaAnnotationBeanPostProcessor implements MergedBeanDefinitionPostProcessor,ApplicationContextAware {

	private ApplicationContext context;
	private AutowireCapableBeanFactory factory;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
		factory = applicationContext.getAutowireCapableBeanFactory();
	}
	
	@Override
	public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
		if(context == null || factory == null) return;
		
		
	}




}
