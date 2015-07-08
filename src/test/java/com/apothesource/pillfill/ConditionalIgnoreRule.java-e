/*
 *
 *  * The MIT License
 *  *
 *  * Copyright {$YEAR} Apothesource, Inc.
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  * THE SOFTWARE.
 *
 */

package com.apothesource.pillfill;

/**
 * Created by rammic on 7/7/15.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;

import org.junit.Assume;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class ConditionalIgnoreRule implements MethodRule {

    public interface IgnoreCondition {
        boolean isSatisfied();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface ConditionalIgnore {
        Class<? extends IgnoreCondition> condition();
    }

    @Override
    public Statement apply( Statement base, FrameworkMethod method, Object target ) {
        Statement result = base;
        if( hasConditionalIgnoreAnnotation( method ) ) {
            IgnoreCondition condition = getIgnoreContition( target, method );
            if( condition.isSatisfied() ) {
                result = new IgnoreStatement( condition );
            }
        }
        return result;
    }

    private static boolean hasConditionalIgnoreAnnotation( FrameworkMethod method ) {
        return method.getAnnotation( ConditionalIgnore.class ) != null;
    }

    private static IgnoreCondition getIgnoreContition( Object target, FrameworkMethod method ) {
        ConditionalIgnore annotation = method.getAnnotation( ConditionalIgnore.class );
        return new IgnoreConditionCreator( target, annotation ).create();
    }

    private static class IgnoreConditionCreator {
        private final Object target;
        private final Class<? extends IgnoreCondition> conditionType;

        IgnoreConditionCreator( Object target, ConditionalIgnore annotation ) {
            this.target = target;
            this.conditionType = annotation.condition();
        }

        IgnoreCondition create() {
            checkConditionType();
            try {
                return createCondition();
            } catch( RuntimeException re ) {
                throw re;
            } catch( Exception e ) {
                throw new RuntimeException( e );
            }
        }

        private IgnoreCondition createCondition() throws Exception {
            IgnoreCondition result;
            if( isConditionTypeStandalone() ) {
                result = conditionType.newInstance();
            } else {
                result = conditionType.getDeclaredConstructor( target.getClass() ).newInstance( target );
            }
            return result;
        }

        private void checkConditionType() {
            if( !isConditionTypeStandalone() && !isConditionTypeDeclaredInTarget() ) {
                String msg
                        = "Conditional class '%s' is a member class "
                        + "but was not declared inside the test case using it.\n"
                        + "Either make this class a static class, "
                        + "standalone class (by declaring it in it's own file) "
                        + "or move it inside the test case using it";
                throw new IllegalArgumentException( String.format ( msg, conditionType.getName() ) );
            }
        }

        private boolean isConditionTypeStandalone() {
            return !conditionType.isMemberClass() || Modifier.isStatic( conditionType.getModifiers() );
        }

        private boolean isConditionTypeDeclaredInTarget() {
            return target.getClass().isAssignableFrom( conditionType.getDeclaringClass() );
        }
    }

    private static class IgnoreStatement extends Statement {
        private final IgnoreCondition condition;

        IgnoreStatement( IgnoreCondition condition ) {
            this.condition = condition;
        }

        @Override
        public void evaluate() {
            Assume.assumeTrue( "Ignored by " + condition.getClass().getSimpleName(), false );
        }
    }

}
