import { useKeycloak } from '@react-keycloak/web';

export const withHooksKC = (Component) => {
    return (props) => {
        const keycloakObject = useKeycloak();
        const initialized = keycloakObject["initialized"]
        const keycloak = keycloakObject["keycloak"]

        return <Component initialized={initialized} keycloak={keycloak} {...props}/>;
    };
};
