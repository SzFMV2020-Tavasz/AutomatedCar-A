package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;


public class DependentVirtualFunctionBus extends VirtualFunctionBus {

    private static final boolean ALLOW_DERIVED_DEPENDENCY_SATISFYING = true;

    /**All simple components - in the sense, that they have no dependencies for other components.*/
    private final List<SystemComponent> nonDependentComponents = new ArrayList<>();

    /**All components, which have at least one dependency for other components.*/
    private final List<DependentComponent> dependentComponents = new ArrayList<>();

    /**
     * Registers the provided {@link SystemComponent component}.
     *
     * @param component component to be registed.
     */
    @Override
    public void registerComponent(SystemComponent component) {
        DependsOn dependencies = component.getClass().getAnnotation(DependsOn.class);

        if (dependencies == null) {
            nonDependentComponents.add(component);
        } else {
            registerComponentWithDependencies(new DependentComponent(component, dependencies));
        }
    }

    private void registerComponentWithDependencies(DependentComponent component) {
        Class<? extends SystemComponent> newComponentType = component.getComponent().getClass();
        List<Class<? extends SystemComponent>> dependencies = asList(component.dependencies.components());

        List<DependentComponent> dependees = dependentComponents.stream()
                .filter(it -> asList(it.dependencies.components()).contains(newComponentType))
                .collect(toList());

        Optional<? extends Class<? extends SystemComponent>> circularDependent = dependees.stream()
                .map(dependee -> dependee.component.getClass())
                .filter(dependencies::contains)
                .findFirst();

        if (circularDependent.isPresent()) {
            throw new IllegalStateException("Circular dependency of components: '"
                    + newComponentType.getSimpleName() + "' and '"
                    + circularDependent.get().getSimpleName() + "'!");
        }

        dependentComponents.removeAll(dependees);
        dependentComponents.add(component);
        dependentComponents.addAll(dependees);
    }

    /**
     * Calls cyclically the registered {@link SystemComponent component}s once the virtual function bus has started.
     */
    @Override
    public void loop() {
        for (SystemComponent component : nonDependentComponents) {
            component.loop();
        }
        for (DependentComponent dependentComponent : dependentComponents) {
            dependentComponent.component.loop();
        }
    }

    /**A small class for mapping components to their dependencies defined via {@link DependsOn} annotation.*/
    private static class DependentComponent {
        private final SystemComponent component;
        private final DependsOn dependencies;

        DependentComponent(SystemComponent component, DependsOn dependencies) {
            this.component = component;
            this.dependencies = dependencies;
        }

        public SystemComponent getComponent() {
            return component;
        }
    }

    public Optional<Map.Entry<SystemComponent, Class<? extends SystemComponent>>> findAnyUnsatisfiedDependency() {
        int dependentComponentCount = dependentComponents.size();

        DependentComponent dependent;
        Map.Entry<SystemComponent, Class<? extends SystemComponent>> unsatisfiedDependency;
        for (int dependentIdx = 0; dependentIdx < dependentComponentCount; ++dependentIdx) {
            dependent = dependentComponents.get(dependentIdx);
            unsatisfiedDependency = findAnyUnsatisfiedDependency(dependentIdx, dependent);
            if (unsatisfiedDependency != null) {
                return Optional.of(unsatisfiedDependency);
            }
        }
        return Optional.empty();
    }

    public boolean areAllDependenciesSatisfied() {
        return findAnyUnsatisfiedDependency().isEmpty();
    }

    public void validateAllDependenciesSatisfied() {
        Optional<Map.Entry<SystemComponent, Class<? extends SystemComponent>>> unsatisfiedDependency = findAnyUnsatisfiedDependency();
        if (unsatisfiedDependency.isPresent()) {
            SystemComponent unsatisfiedDependent = unsatisfiedDependency.get().getKey();
            Class<? extends SystemComponent> dependency = unsatisfiedDependency.get().getValue();
            throw new IllegalStateException("Component '"
                    + unsatisfiedDependent.getClass().getSimpleName() + "' has unsatisfied dependency for component class: '"
                    + dependency.getSimpleName() + "' !");
        }
    }

    /**@return {@code null} in case all dependencies are satisfied.*/
    private Map.Entry<SystemComponent, Class<? extends SystemComponent>> findAnyUnsatisfiedDependency(int index, DependentComponent dependent) {
        for (Class<? extends SystemComponent> dependency : dependent.dependencies.components()) {
            Stream<Class<? extends SystemComponent>> componentsPresent = Stream.concat(
                    nonDependentComponents.stream()
                            .map(SystemComponent::getClass),
                    dependentComponents.stream()
                            .limit(index)
                            .map(DependentComponent::getComponent)
                            .map(SystemComponent::getClass));

            Predicate<Class<? extends SystemComponent>> satisfactionCriteria = ALLOW_DERIVED_DEPENDENCY_SATISFYING
                    ? dependency::isAssignableFrom
                    : dependency::equals;

            if (componentsPresent.noneMatch(satisfactionCriteria)) {
                return new SimpleEntry<>(dependent.component, dependency);
            }

        }
        return null;
    }

}
