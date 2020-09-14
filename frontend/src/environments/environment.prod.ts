import { PlayerDto } from 'src/app/api';

export const environment = {
  production: true,
  websocket: "ws://localhost:8080/socket-registry",
  basePath: "http://localhost:8080"
};
export var apiConfig = {
  basePath: environment.basePath,
  accessToken: null
};
export var player:PlayerDto = {};
