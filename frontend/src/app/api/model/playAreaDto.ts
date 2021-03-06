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


export interface PlayAreaDto { 
    currentPlayer?: PlayAreaDto.CurrentPlayerEnum;
    seedstorePlayerOne?: number;
    seedstorePlayerTwo?: number;
    housesPlayerOne?: Array<number>;
    housesPlayerTwo?: Array<number>;
}
export namespace PlayAreaDto {
    export type CurrentPlayerEnum = 'One' | 'Two' | 'None';
    export const CurrentPlayerEnum = {
        One: 'One' as CurrentPlayerEnum,
        Two: 'Two' as CurrentPlayerEnum,
        None: 'None' as CurrentPlayerEnum
    };
}


