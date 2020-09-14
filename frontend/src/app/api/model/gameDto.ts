/**
 * Kalah
 * Kalah Game
 *
 * The version of the OpenAPI document: v0.0.1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { PlayerInfo } from './playerInfo';


export interface GameDto { 
    gameId?: string;
    state?: GameDto.StateEnum;
    winner?: GameDto.WinnerEnum;
    playerOne?: PlayerInfo;
    playerTwo?: PlayerInfo;
}
export namespace GameDto {
    export type StateEnum = 'AWAITING_PLAYER' | 'ONGOING' | 'ENDED';
    export const StateEnum = {
        AWAITINGPLAYER: 'AWAITING_PLAYER' as StateEnum,
        ONGOING: 'ONGOING' as StateEnum,
        ENDED: 'ENDED' as StateEnum
    };
    export type WinnerEnum = 'One' | 'Two' | 'None';
    export const WinnerEnum = {
        One: 'One' as WinnerEnum,
        Two: 'Two' as WinnerEnum,
        None: 'None' as WinnerEnum
    };
}


